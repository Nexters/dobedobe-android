package com.chipichipi.dobedobe.feature.dashboard

import android.os.Build
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBottomSheetScaffold
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeDialog
import com.chipichipi.dobedobe.feature.dashboard.component.CollapsedPhotoFrame
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardBubble
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardCharacter
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardEditModeTopAppBar
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardTopAppBar
import com.chipichipi.dobedobe.feature.dashboard.component.EditModePhotoFrame
import com.chipichipi.dobedobe.feature.dashboard.component.ExpandedPhotoFrame
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.skydoves.cloudy.cloudy
import org.koin.androidx.compose.koinViewModel

private const val ANIMATION_DURATION = 500

@Composable
internal fun DashboardRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToAddGoal: () -> Unit,
    navigateToGoalDetail: (Long) -> Unit,
    navigateToSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashboardScreen(
        modifier = modifier.fillMaxSize(),
        onShowSnackbar = onShowSnackbar,
        uiState = uiState,
        setGoalNotificationEnabled = viewModel::setGoalNotificationEnabled,
        disableSystemNotificationDialog = viewModel::disableSystemNotificationDialog,
        navigateToAddGoal = navigateToAddGoal,
        navigateToGoalDetail = navigateToGoalDetail,
        navigateToSetting = navigateToSetting,
        onGoalToggled = viewModel::toggleGoalCompletion,
        onToggleMode = viewModel::toggleMode,
    )
}

@Composable
private fun DashboardScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    uiState: DashboardUiState,
    setGoalNotificationEnabled: (Boolean) -> Unit,
    disableSystemNotificationDialog: () -> Unit,
    navigateToAddGoal: () -> Unit,
    navigateToGoalDetail: (Long) -> Unit,
    navigateToSetting: () -> Unit,
    onGoalToggled: (Long) -> Unit,
    onToggleMode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
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
                    navigateToAddGoal = navigateToAddGoal,
                    navigateToGoalDetail = navigateToGoalDetail,
                    navigateToSetting = navigateToSetting,
                    onGoalToggled = onGoalToggled,
                    onToggleMode = onToggleMode,
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
    navigateToAddGoal: () -> Unit,
    navigateToGoalDetail: (Long) -> Unit,
    navigateToSetting: () -> Unit,
    onGoalToggled: (Long) -> Unit,
    onToggleMode: () -> Unit,
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
        val photoFramesState = rememberDashboardPhotoFramesState(
            photoState = uiState.photoState,
        )
        val onToggleExpansion: (Int) -> Unit = { id ->
            photoFramesState.rotationMap[id]?.let { rotation ->
                if (!rotation.isRunning) {
                    photoFramesState.toggleExpansion(id)
                }
            }
        }
        val isEditMode = uiState.mode.isEditMode

        DobeDobeBottomSheetScaffold(
            modifier = modifier
                .fillMaxSize()
                .then(
                    if (isEditMode) {
                        Modifier.cloudy(35)
                    } else {
                        Modifier
                    },
                ),
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                GoalBottomSheetContent(
                    goals = uiState.goals,
                    onGoalToggled = onGoalToggled,
                    onAddGoalClicked = navigateToAddGoal,
                    onGoalClicked = navigateToGoalDetail,
                )
            },
            sheetPeekHeight = 380.dp,
            topBar = {
                DashboardTopAppBar(
                    onEditClick = onToggleMode,
                    navigateToSetting = navigateToSetting,
                )
            },
        ) { innerPadding ->
            DashboardViewMode(
                isViewMode = uiState.mode.isViewMode,
                photoState = uiState.photoState,
                photoFramesState = photoFramesState,
                onToggleExpansion = onToggleExpansion,
                onToggleMode = onToggleMode,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        }

        ExpandedPhotoFrame(
            photoState = photoFramesState.currentPhotoId?.let { id ->
                uiState.photoState[id - 1]
            },
            rotation = photoFramesState.rotationMap[photoFramesState.currentPhotoId],
            onToggleExpansion = { id ->
                if (!isEditMode) onToggleExpansion(id)
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
        )

        if (isEditMode) {
            DashboardEditMode(
                photoState = uiState.photoState,
                onToggleMode = onToggleMode,
            )
        }

        DashboardPhotoRotationEffect(
            photoStates = uiState.photoState,
            rotationMap = photoFramesState.rotationMap,
            photoFramesState = photoFramesState,
        )
    }

    GoalNotificationPermission(
        isSystemNotificationDialogDisabled = uiState.isSystemNotificationDialogDisabled,
        setGoalNotificationEnabled = setGoalNotificationEnabled,
        disableSystemNotificationDialog = disableSystemNotificationDialog,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.DashboardViewMode(
    isViewMode: Boolean,
    photoState: List<DashboardPhotoState>,
    photoFramesState: DashboardPhotoFramesState,
    onToggleExpansion: (Int) -> Unit,
    onToggleMode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        photoState.forEach { photo ->
            CollapsedPhotoFrame(
                config = photo.config,
                url = photo.url,
                isExpanded = photoFramesState.isExpanded(photo.config.id),
                rotation = photoFramesState.rotationMap[photo.config.id],
                onToggleExpansion = onToggleExpansion,
                onEmptyFrameClick = onToggleMode,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f),
            )
        }

        if (isViewMode) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(13.dp))
                DashboardBubble(
                    // TODO: Goal Title 연동하기
                    title = "먹고자고놀고먹고자고놀고힘낼거야",
                    //TODO : font 적용
                    textStyle = TextStyle(fontSize = 15.sp),
                    // TODO: ColorScheme 적용
                    modifier = Modifier.background(Color.White),
                    // TODO: Detail 화면으로 이동
                    onClick = {},
                )
                DashboardCharacter(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(0.5f),
                )
            }
        }
    }
}

@Composable
private fun DashboardEditMode(
    photoState: List<DashboardPhotoState>,
    onToggleMode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO : 색상 변경 필요
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.7f)),
    ) {
        DashboardEditModeTopAppBar(
            onToggleMode = onToggleMode,
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            photoState.forEach { photo ->
                // TODO : EmptyFrameClick 처리
                EditModePhotoFrame(
                    config = photo.config,
                    url = photo.url,
                    rotation = photo.config.rotationZ,
                    onClick = {},
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 230.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // TODO : 색상 변경 필요
                Icon(
                    painter = painterResource(R.drawable.ic_dashboard_edit_mode),
                    tint = Color.White,
                    contentDescription = "edit mode icon",
                )

                Text(
                    text = stringResource(R.string.feature_dashboard_edit_mode_description),
                    fontSize = 16.sp,
                    color = Color.White,
                )
            }
        }
    }
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

@Composable
private fun DashboardPhotoRotationEffect(
    photoStates: List<DashboardPhotoState>,
    rotationMap: Map<Int, Animatable<Float, AnimationVector1D>>,
    photoFramesState: DashboardPhotoFramesState,
) {
    suspend fun animatePhotoRotation(photoId: Int?, isExpanded: Boolean) {
        photoId?.let { id ->
            val targetValue =
                if (isExpanded) 0f else photoStates[id - 1].config.rotationZ

            rotationMap[id]?.animateTo(
                targetValue = targetValue,
                animationSpec = tween(durationMillis = ANIMATION_DURATION),
            )
        }
    }

    LaunchedEffect(photoFramesState.currentPhotoId) {
        animatePhotoRotation(
            photoId = photoFramesState.currentPhotoId,
            isExpanded = photoFramesState.isExpanded(photoFramesState.currentPhotoId),
        )
    }

    LaunchedEffect(photoFramesState.previousPhotoId) {
        animatePhotoRotation(
            photoId = photoFramesState.previousPhotoId,
            isExpanded = photoFramesState.isExpanded(photoFramesState.previousPhotoId),
        )
    }
}
