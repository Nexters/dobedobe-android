package com.chipichipi.dobedobe.feature.dashboard

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.DashboardRepository
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardModeState
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

internal class DashboardViewModel(
    private val userRepository: UserRepository,
    private val goalRepository: GoalRepository,
    private val dashboardRepository: DashboardRepository,
) : ViewModel() {
    private val isSystemNotificationDialogDisabledFlow = userRepository.userData
        .map { it.isSystemNotificationDialogDisabled }
        .distinctUntilChanged()

    private val character = userRepository.userData
        .map { it.characterType }
        .distinctUntilChanged()

    private val _modeState: MutableStateFlow<DashboardModeState> =
        MutableStateFlow(DashboardModeState.View)
    val modeState: StateFlow<DashboardModeState> = _modeState.asStateFlow()

    private val changeBubbleEvent: Channel<Unit> = Channel(capacity = Channel.BUFFERED)
    private val bubbleGoal: StateFlow<BubbleGoal> = bubbleGoalFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BubbleGoal.empty(),
    )

    val uiState: StateFlow<DashboardUiState> = combine(
        dashboardRepository.getPhotos(),
        isSystemNotificationDialogDisabledFlow,
        goalRepository.getSortedGoals(),
        bubbleGoal,
        character,
    ) { photoState, isSystemNotificationDialogDisabled, goals, bubbleGoal, character ->
        val dashboardPhotoStates = DashboardPhotoConfig.entries.map { config ->
            val photo = photoState.find { it.id == config.id }
            val uri = photo?.path?.let { Uri.fromFile(File(it)) } ?: Uri.EMPTY

            DashboardPhotoState(
                config = config,
                uri = uri,
            )
        }

        DashboardUiState.Success(
            photoState = dashboardPhotoStates,
            isSystemNotificationDialogDisabled = isSystemNotificationDialogDisabled,
            goals = goals,
            bubbleTitle = bubbleGoal.title,
            character = character,
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState.Loading,
        )

    fun setGoalNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userRepository.setGoalNotificationEnabled(enabled)
        }
    }

    fun disableSystemNotificationDialog() {
        viewModelScope.launch {
            userRepository.disableSystemNotificationDialog()
        }
    }

    fun toggleGoalCompletion(id: Long) {
        viewModelScope.launch {
            goalRepository.toggleCompletion(id)
                .onFailure {
                    // TODO : Error 처리
                    Log.e("DashboardViewModel", "Fail to toggle Goal", it)
                }
        }
    }

    fun toggleMode() {
        if (uiState.value is DashboardUiState.Success) {
            viewModelScope.launch {
                _modeState.update { currentState ->
                    if (currentState is DashboardModeState.View) {
                        DashboardModeState.Edit(
                            drafts = (uiState.value as DashboardUiState.Success).photoState,
                        )
                    } else {
                        DashboardModeState.View
                    }
                }
            }
        }
    }

    fun upsertPhotos(photos: List<DashboardPhoto>) {
        viewModelScope.launch {
            dashboardRepository.upsertPhotos(photos)
        }
    }

    fun deletePhotos(photos: List<DashboardPhoto>) {
        viewModelScope.launch {
            val ids = photos.map { it.id }
            dashboardRepository.deletePhotosByIds(ids)
        }
    }

    fun updatePhotoDrafts(id: Int?, uri: Uri) {
        require(_modeState.value is DashboardModeState.Edit) {
            "updatePhotoDrafts() should only be called in Edit mode"
        }

        viewModelScope.launch {
            _modeState.update { state ->
                (state as DashboardModeState.Edit).copy(
                    drafts = state.drafts.map { draft ->
                        if (draft.config.id == id) {
                            draft.copy(
                                uri = uri,
                                hasUriChanged = true,
                            )
                        } else {
                            draft
                        }
                    },
                )
            }
        }
    }

    fun changeBubble() {
        viewModelScope.launch {
            changeBubbleEvent.send(Unit)
        }
    }

    /**
     * bubbleGoal 을 random 으로 변경하는 로직
     *
     * - todoGoals 가 empty 일 경우, Empty BubbleGoal 을 반환
     * - todoGoals 가 empty 가 아닌 경우
     *
     * 1) goalRepository 에 있는 goal 이 변경될 경우
     *      1-1) old goals 와 new goals 가 다르면 변경
     *      1-2) 만약, bubbleGoal 의 id 가 todoGoals 에 포함되어 있으면 변경
     *
     * 2) changeBubbleEvent 가 발생할 경우
     *      - 무조건 random 하게 변경
     * */
    private fun bubbleGoalFlow(): Flow<BubbleGoal> {
        val todoGoals: Flow<List<Goal>> = goalRepository.getTodoGoals()
            .distinctUntilChanged()
            .distinctUntilChanged { _, new ->
                val id = bubbleGoal.value.id
                val isBubbleGoalCompleted = new.any { it.id == id }
                isBubbleGoalCompleted
            }

        return combine(
            todoGoals,
            changeBubbleEvent.receiveAsFlow().onStart {
                emit(Unit)
            },
        ) { goals, _ ->
            goals.filter { it.id != bubbleGoal.value.id }.shuffled()
        }
            .map(List<Goal>::firstOrNull)
            .map { goal ->
                if (goal != null) {
                    BubbleGoal.from(goal)
                } else {
                    BubbleGoal.empty()
                }
            }
    }

    private data class BubbleGoal(
        val title: String,
        val id: Long?,
    ) {
        companion object {
            private val Empty = BubbleGoal(title = "", id = null)

            fun empty(): BubbleGoal = Empty

            fun from(goal: Goal): BubbleGoal {
                return BubbleGoal(goal.title, goal.id)
            }
        }
    }
}
