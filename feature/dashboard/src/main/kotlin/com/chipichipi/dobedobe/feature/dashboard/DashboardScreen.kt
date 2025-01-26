package com.chipichipi.dobedobe.feature.dashboard

import android.os.Build
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBottomSheetScaffold
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeDialog
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardCharacter
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardPhotoFrameBox
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardTopAppBar
import com.chipichipi.dobedobe.feature.dashboard.preview.GoalPreviewParameterProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DashboardRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashboardScreen(
        modifier = modifier,
        onShowSnackbar = onShowSnackbar,
        uiState = uiState,
        setGoalNotificationChecked = viewModel::setGoalNotificationChecked,
        disableSystemNotificationDialog = viewModel::disableSystemNotificationDialog,
        navigateToSetting = navigateToSetting
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    uiState: DashboardUiState,
    setGoalNotificationChecked: (Boolean) -> Unit,
    disableSystemNotificationDialog: () -> Unit,
    navigateToSetting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
        ),
    )
    val photoFramesState = rememberDashboardPhotoFramesState()

    DobeDobeBottomSheetScaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            GoalBottomSheetContent(
                goals = GoalPreviewParameterProvider.fakeGoals(20),
                // TODO 임시 goal 데이터
                onGoalDone = {},
                onGoalClicked = {},
            )
        },
        // TODO 임시 peekHeight 값
        sheetPeekHeight = 200.dp,
        topBar = {
            // TODO: 기능 추가 필요
            DashboardTopAppBar(
                onEditClick = {},
                onSettingClick = navigateToSetting
            )
        },
    ) { innerPadding ->
        // Dim 처리로 인해 innerPadding은 Box에 적용 안하고 우선 component별로 각각 적용하도록 처리
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (uiState) {
                is DashboardUiState.Error,
                is DashboardUiState.Loading,
                -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                    )
                }
                is DashboardUiState.Success -> {
                    DashboardBody(
                        uiState = uiState,
                        photoFramesState = photoFramesState,
                        innerPadding = innerPadding,
                        setGoalNotificationChecked = setGoalNotificationChecked,
                        disableSystemNotificationDialog = disableSystemNotificationDialog,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DashboardBody(
    uiState: DashboardUiState.Success,
    photoFramesState: DashboardPhotoFramesState,
    innerPadding: PaddingValues,
    setGoalNotificationChecked: (Boolean) -> Unit,
    disableSystemNotificationDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SharedTransitionLayout(
        modifier = modifier
            // TODO : 색상 변경 필요
            .background(
                color = Color(0xFFFDFDFD),
            ),
    ) {
        DashboardCharacter(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 100.dp)
                .zIndex(0.5f),
        )

        uiState.photoState.forEach { photo ->
            // TODO : EmptyFrameClick 처리
            DashboardPhotoFrameBox(
                photo = photo,
                isExpanded = photoFramesState.isExpanded(photo.config.id),
                toggleExpansion = photoFramesState::toggleExpansion,
                onEmptyFrameClick = { },
                innerPadding = innerPadding,
            )
        }
    }

    GoalNotificationPermissionEffect(
        isSystemNotificationDialogDisabled = uiState.isSystemNotificationDialogDisabled,
        setGoalNotificationChecked = setGoalNotificationChecked,
        disableSystemNotificationDialog = disableSystemNotificationDialog,
    )
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun GoalNotificationPermissionEffect(
    isSystemNotificationDialogDisabled: Boolean,
    setGoalNotificationChecked: (Boolean) -> Unit,
    disableSystemNotificationDialog: () -> Unit,
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    val notificationsPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS,
    )
    var showGoalNotificationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(notificationsPermissionState.status, isSystemNotificationDialogDisabled) {
        if (isSystemNotificationDialogDisabled) return@LaunchedEffect
        val status = notificationsPermissionState.status

        when {
            status is PermissionStatus.Denied && !status.shouldShowRationale -> {
                showGoalNotificationDialog = true
            }
            status is PermissionStatus.Denied && status.shouldShowRationale -> {
                setGoalNotificationChecked(false)
                disableSystemNotificationDialog()
            }
            status.isGranted -> {
                setGoalNotificationChecked(true)
                disableSystemNotificationDialog()
            }
        }
    }

    if (showGoalNotificationDialog) {
        DobeDobeDialog(
            onDismissRequest = {
                showGoalNotificationDialog = false
            },
            // TODO : 변경 필요
            title = "목표에 대한 알림을 위해\n 권한이 필요합니다.",
        ) {
            Button(
                onClick = {
                    notificationsPermissionState.launchPermissionRequest()
                    showGoalNotificationDialog = false
                },
            ) {
                // TODO : 변경 필요
                Text("확인")
            }
        }
    }
}
