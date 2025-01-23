package com.chipichipi.dobedobe.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.model.DashboardImage
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// TODO : 제거 필요
private val fakeDashboardImageState = MutableStateFlow(
    listOf(
        DashboardImage(1, "https://picsum.photos/id/237/200/300"),
        DashboardImage(2, "https://picsum.photos/id/233/200/300"),
    ),
)

class DashboardViewModel(

) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = fakeDashboardImageState
        .map { imageData ->
            val dashboardPhotoStates = DashboardPhotoConfig.entries.map { definition ->
                val matchingData = imageData.find { it.id == definition.id }

                DashboardPhotoState(
                    config = definition,
                    imageUrl = matchingData?.imageUrl.orEmpty(),
                )
            }
            DashboardUiState.Success(dashboardPhotoStates)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState.Loading,
        )
}
