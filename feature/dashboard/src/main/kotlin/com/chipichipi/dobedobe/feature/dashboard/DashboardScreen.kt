package com.chipichipi.dobedobe.feature.dashboard

import android.os.Build
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBottomSheetScaffold
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeDialog
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.dashboard.component.CollapsedPhotoFrame
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardCharacter
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardTopAppBar
import com.chipichipi.dobedobe.feature.dashboard.component.ExpandedPhotoFrame
import com.chipichipi.dobedobe.feature.dashboard.preview.GoalPreviewParameterProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel

private const val ANIMATION_DURATION = 500

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
        setGoalNotificationEnabled = viewModel::setGoalNotificationEnabled,
        disableSystemNotificationDialog = viewModel::disableSystemNotificationDialog,
        navigateToSetting = navigateToSetting,
        onGoalToggled = viewModel::toggleGoalCompletion,
    )
}

@Composable
private fun DashboardScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    uiState: DashboardUiState,
    setGoalNotificationEnabled: (Boolean) -> Unit,
    disableSystemNotificationDialog: () -> Unit,
    navigateToSetting: () -> Unit,
    onGoalToggled: (Goal) -> Unit,
    modifier: Modifier = Modifier,
) {
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
                    modifier = modifier,
                    uiState = uiState,
                    setGoalNotificationEnabled = setGoalNotificationEnabled,
                    disableSystemNotificationDialog = disableSystemNotificationDialog,
                    navigateToSetting = navigateToSetting,
                    onGoalToggled = onGoalToggled,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun DashboardBody(
    uiState: DashboardUiState.Success,
    setGoalNotificationEnabled: (Boolean) -> Unit,
    disableSystemNotificationDialog: () -> Unit,
    navigateToSetting: () -> Unit,
    onGoalToggled: (Goal) -> Unit,
    modifier: Modifier = Modifier,
) {
    SharedTransitionLayout(
        modifier = modifier,
    ) {
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.PartiallyExpanded,
            ),
        )
        val photoFramesState = rememberDashboardPhotoFramesState()

        val rotationMap = remember {
            uiState.photoState.associate { it.config.id to Animatable(it.config.rotationZ) }
        }

        val onToggleExpansion: (Int) -> Unit = { id ->
            val rotation = rotationMap[id]

            if (rotation != null && !rotation.isRunning) {
                photoFramesState.toggleExpansion(id)
            }
        }

        LaunchedEffect(photoFramesState.currentPhoto) {
            photoFramesState.currentPhoto?.let { photo ->
                val targetValue = if (photoFramesState.isExpanded(photo)) {
                    0f
                } else {
                    uiState.photoState[photo - 1].config.rotationZ
                }

                rotationMap[photo]?.animateTo(
                    targetValue = targetValue,
                    animationSpec = tween(durationMillis = ANIMATION_DURATION),
                )
            }
        }

        LaunchedEffect(photoFramesState.previousPhoto) {
            photoFramesState.previousPhoto?.let { photo ->
                val targetValue = if (!photoFramesState.isExpanded(photo)) {
                    uiState.photoState[photo - 1].config.rotationZ
                } else {
                    0f
                }

                rotationMap[photo]?.animateTo(
                    targetValue = targetValue,
                    animationSpec = tween(durationMillis = ANIMATION_DURATION),
                )
            }
        }

        DobeDobeBottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                GoalBottomSheetContent(
                    goals = uiState.goals,
                    onGoalToggled = onGoalToggled,
                    // TODO : navigateToGoalDetail 연결 필요
                    onGoalClicked = { },
            },
            sheetPeekHeight = 380.dp,
            topBar = {
                // TODO: 기능 추가 필요
                DashboardTopAppBar(
                    onEditClick = {},
                    navigateToSetting = navigateToSetting,
                )
            },
        ) { innerPadding ->
            // Dim 처리로 인해 innerPadding은 Box에 적용 안하고 우선 component별로 각각 적용하도록 처리
            DashboardCharacter(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 110.dp)
                    .zIndex(0.5f),
            )

            uiState.photoState.forEach { photo ->
                // TODO : EmptyFrameClick 처리
                CollapsedPhotoFrame(
                    photo = photo,
                    isExpanded = photoFramesState.isExpanded(photo.config.id),
                    rotationMap = rotationMap,
                    onToggleExpansion = photoFramesState::toggleExpansion,
                    onEmptyFrameClick = { },
                    innerPadding = innerPadding,
                )
            }
        }

        ExpandedPhotoFrame(
            rotation = rotationMap,
            state = if (photoFramesState.currentPhoto == null) {
                null
            } else {
                uiState.photoState[photoFramesState.currentPhoto!! - 1]
            },
            onToggleExpansion = onToggleExpansion,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
        )
    }

    GoalNotificationPermission(
        isSystemNotificationDialogDisabled = uiState.isSystemNotificationDialogDisabled,
        setGoalNotificationEnabled = setGoalNotificationEnabled,
        disableSystemNotificationDialog = disableSystemNotificationDialog,
    )
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun GoalNotificationPermission(
    isSystemNotificationDialogDisabled: Boolean,
    setGoalNotificationEnabled: (Boolean) -> Unit,
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
                setGoalNotificationEnabled(false)
                disableSystemNotificationDialog()
            }

            status.isGranted -> {
                setGoalNotificationEnabled(true)
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
