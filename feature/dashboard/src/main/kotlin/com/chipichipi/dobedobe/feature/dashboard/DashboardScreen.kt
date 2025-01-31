package com.chipichipi.dobedobe.feature.dashboard

import android.net.Uri
import android.os.Build
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DRAG_HANDLER_HEIGHT
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBottomSheetScaffold
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeDialog
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardEditMode
import com.chipichipi.dobedobe.feature.dashboard.component.DashboardViewMode
import com.chipichipi.dobedobe.feature.dashboard.component.ExpandedPhotoFrame
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardModeState
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
    val modeState by viewModel.modeState.collectAsStateWithLifecycle()

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
        onUpsertPhotos = viewModel::upsertPhotos,
        onDeletePhotos = viewModel::deletePhotos,
        onChangeBubble = viewModel::changeBubble,
        modeState = modeState,
        onUpdatePhotoDrafts = viewModel::updatePhotoDrafts,
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
    onUpsertPhotos: (List<DashboardPhoto>) -> Unit,
    onDeletePhotos: (List<DashboardPhoto>) -> Unit,
    onChangeBubble: () -> Unit,
    modeState: DashboardModeState,
    onUpdatePhotoDrafts: (Int?, Uri) -> Unit,
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
                    onChangeBubble = onChangeBubble,
                    onToggleMode = onToggleMode,
                    onUpsertPhotos = onUpsertPhotos,
                    onDeletePhotos = onDeletePhotos,
                    modeState = modeState,
                    onUpdatePhotoDrafts = onUpdatePhotoDrafts,
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
    onChangeBubble: () -> Unit,
    onToggleMode: () -> Unit,
    onUpsertPhotos: (List<DashboardPhoto>) -> Unit,
    onDeletePhotos: (List<DashboardPhoto>) -> Unit,
    modeState: DashboardModeState,
    onUpdatePhotoDrafts: (Int?, Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEditMode = modeState is DashboardModeState.Edit

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

        DobeDobeBottomSheetScaffold(
            modifier = Modifier
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
                    modifier = Modifier
                        .padding(top = 8.dp),
                )
            },
            sheetPeekHeight = 380.dp,
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = innerPadding.calculateBottomPadding() - DRAG_HANDLER_HEIGHT,
                    ),
            ) {
                DashboardViewMode(
                    isViewMode = !isEditMode,
                    photoState = uiState.photoState,
                    bubbleTitle = uiState.bubbleTitle,
                    photoFramesState = photoFramesState,
                    onChangeBubble = onChangeBubble,
                    onToggleExpansion = onToggleExpansion,
                    onToggleMode = onToggleMode,
                    navigateToSetting = navigateToSetting,
                )
            }
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

        DashboardPhotoRotationEffect(
            photoStates = uiState.photoState,
            rotationMap = photoFramesState.rotationMap,
            photoFramesState = photoFramesState,
        )
    }

    if (modeState is DashboardModeState.Edit) {
        DashboardEditMode(
            modeState = modeState,
            onToggleMode = onToggleMode,
            onUpsertPhotos = onUpsertPhotos,
            onDeletePhotos = onDeletePhotos,
            onUpdatePhotoDrafts = onUpdatePhotoDrafts,
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
            title = stringResource(R.string.feature_dashboard_goal_notification_prompt),
            description = stringResource(R.string.feature_dashboard_goal_notification_description),
            header = {
                Icon(
                    painter = painterResource(R.drawable.ic_alarm_64),
                    contentDescription = "alarm icon",
                    tint = Color.Unspecified,
                )
            },
            primaryText = stringResource(R.string.feature_dashboard_goal_notification_confirm_text),
            onClickPrimary = {
                notificationsPermissionState.launchPermissionRequest()
                showGoalNotificationDialog = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        )
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
