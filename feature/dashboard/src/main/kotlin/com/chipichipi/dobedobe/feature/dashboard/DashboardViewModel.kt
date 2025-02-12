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
import com.chipichipi.dobedobe.feature.dashboard.model.BubbleGoal
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
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
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
            bubbleGoal = bubbleGoal,
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

    private fun bubbleGoalFlow(): Flow<BubbleGoal> {
        val events: Flow<BubbleGoalEvent> = merge(
            goalRepository.getTodoGoals().map { goals -> BubbleGoalEvent.GoalsChanged(goals) },
            changeBubbleEvent.receiveAsFlow().map { BubbleGoalEvent.BubbleChanged },
        )

        return events
            .runningFold(BubbleGoal.empty() to emptyList<Goal>()) { acc, event ->
                val (currentBubble, latestGoals) = acc

                when (event) {
                    is BubbleGoalEvent.GoalsChanged -> handleGoalsChangedEvent(
                        event.goals,
                        currentBubble,
                    )

                    is BubbleGoalEvent.BubbleChanged -> handleBubbleChangedEvent(
                        latestGoals,
                        currentBubble,
                    )
                }
            }
            .map { it.first }
    }

    /**
     * goals 이 변경될 때(e. toggle, delete) BubbleGoal 을 업데이트하는 함수.
     *
     * 1) BubbleGoal 이 goals 에 포함되어 있으면 그대로 유지
     * 2) BubbleGoal 이 goals 에 포함 x
     *    - goals 이 비어있으면 empty 상태로 반환
     *    - goals 이 존재하면 랜덤으로 선택하여 새로운 BubbleGoal 을 생성
     * */
    private fun handleGoalsChangedEvent(
        goals: List<Goal>,
        currentBubble: BubbleGoal,
    ): Pair<BubbleGoal, List<Goal>> {
        val containsCurrentBubble = goals.any { it.id == currentBubble.id }

        if (containsCurrentBubble) {
            // 현재 버블이 여전히 목록에 있으면 그대로 유지
            return currentBubble to goals
        }

        if (goals.isEmpty()) {
            // 목록이 비었으면 empty 상태로 반환
            return BubbleGoal.empty() to goals
        }

        val newGoal = goals.random()
        return BubbleGoal.from(newGoal) to goals
    }

    /**
     * 버블 변경 이벤트(BubbleChanged)가 발생했을 때 현재 BubbleGoal 을 업데이트하는 함수.
     *
     * candidates: 현재 BubbleGoal 을 제외한 후보 목록
     *
     * 1) 후보 목록이 비어있으면 현재 BubbleGoal 을 그대로 반환
     * 2) 후보 목록이 존재하면 후보 목록 중 랜덤으로 선택하여 새로운 BubbleGoal 을 생성
     * */
    private fun handleBubbleChangedEvent(
        goals: List<Goal>,
        currentBubble: BubbleGoal,
    ): Pair<BubbleGoal, List<Goal>> {
        val candidates = goals.filter { it.id != currentBubble.id }

        return if (candidates.isEmpty()) {
            currentBubble to goals
        } else {
            val newGoal = candidates.random()
            BubbleGoal.from(newGoal) to goals
        }
    }

    private sealed class BubbleGoalEvent {
        data class GoalsChanged(val goals: List<Goal>) : BubbleGoalEvent()

        data object BubbleChanged : BubbleGoalEvent()
    }
}
