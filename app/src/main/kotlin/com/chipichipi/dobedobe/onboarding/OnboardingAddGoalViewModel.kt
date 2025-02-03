package com.chipichipi.dobedobe.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.core.model.GoalTitleValidResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class OnboardingAddGoalViewModel : ViewModel() {
    private val _title: MutableStateFlow<String> = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    val titleValidResult: StateFlow<GoalTitleValidResult> =
        _title.map(Goal::validateTitle)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = GoalTitleValidResult.Empty,
            )


    fun changeGoalTitle(newTitle: String) {
        _title.value = newTitle
    }
}
