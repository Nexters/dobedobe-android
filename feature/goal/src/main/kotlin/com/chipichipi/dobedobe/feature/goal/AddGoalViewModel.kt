package com.chipichipi.dobedobe.feature.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.core.model.GoalTitleValidResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddGoalViewModel(
    private val goalRepository: GoalRepository,
) : ViewModel() {

    // TODO: 추후 복잡해지면 UI State를 따로 빼서 관리하는 방식으로 변경
    private val goalTitle: MutableStateFlow<String> = MutableStateFlow("")
    val goalValidResult: StateFlow<GoalTitleValidResult> =
        goalTitle
            .map(Goal::validateTitle)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = GoalTitleValidResult.Empty,
            )

    private val _navigateToBackEvent = Channel<Unit>(capacity = Channel.BUFFERED)
    val navigateToBackEvent: Flow<Unit> = _navigateToBackEvent.receiveAsFlow()

    fun changeGoalTitle(title: String) {
        goalTitle.value = title
    }

    fun addGoal() {
        viewModelScope.launch {
            if (goalValidResult.value.isValid()) {
                goalRepository.addGoal(goalTitle.value).onSuccess {
                    _navigateToBackEvent.send(Unit)
                }
                return@launch
            }
        }
    }
}
