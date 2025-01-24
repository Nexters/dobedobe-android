package com.chipichipi.dobedobe.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// TODO : 제거 필요
private val fakeDashboardPhotoState =
    MutableStateFlow(
        listOf(
            DashboardPhoto(1, "https://picsum.photos/id/237/200/300"),
            DashboardPhoto(2, "https://picsum.photos/id/233/200/300"),
        ),
    )

class DashboardViewModel() : ViewModel() {
    val uiState: StateFlow<DashboardUiState> =
        fakeDashboardPhotoState.map { photoData ->
            val dashboardPhotoStates = DashboardPhotoConfig.entries.map { config ->
                val photo = photoData.find { it.id == config.id }

                DashboardPhotoState(
                    config = config,
                    url = photo?.url.orEmpty(),
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
