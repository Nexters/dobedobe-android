package com.chipichipi.dobedobe.ui

import com.chipichipi.dobedobe.core.model.UserData

sealed interface MainUiState {
    data object Loading : MainUiState

    data object Onboarding : MainUiState

    data class Success(val userData: UserData) : MainUiState
}
