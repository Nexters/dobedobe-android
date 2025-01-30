package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeDialog
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.goal.component.DetailGoalTopAppBar
import com.chipichipi.dobedobe.feature.goal.component.GoalEditor
import com.chipichipi.dobedobe.feature.goal.component.GoalToggleChip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DetailGoalRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailGoalViewModel = koinViewModel(),
) {
    val uiState: DetailGoalUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val focusManager = LocalFocusManager.current
    val onBack = {
        focusManager.clearFocus()
        navigateToBack()
    }

    LaunchedEffect(Unit) {
        viewModel.navigateToBackEvent
            .onEach { onBack() }
            .flowWithLifecycle(lifecycle)
            .launchIn(this)
    }

    DetailGoalScreen(
        uiState = uiState,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            },
        onShowSnackbar = onShowSnackbar,
        navigateToBack = onBack,
        onChangeGoalName = viewModel::changeGoalTitle,
        onTogglePinned = viewModel::togglePinned,
        onToggleCompleted = viewModel::toggleCompleted,
        onRemoveGoal = viewModel::removeGoal,
    )
}

@Composable
private fun DetailGoalScreen(
    uiState: DetailGoalUiState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    onChangeGoalName: (Long, String) -> Unit,
    onTogglePinned: (Long) -> Unit,
    onToggleCompleted: (Long) -> Unit,
    onRemoveGoal: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (visibleDialog, setVisibleDialog) = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            DetailGoalTopAppBar(
                navigateToBack = navigateToBack,
                onRemoveGoal = {
                    if (uiState.isSuccess) setVisibleDialog(true)
                },
            )
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailGoalUiState.Loading, DetailGoalUiState.Error -> {
                Box(
                    modifier = modifier.padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            is DetailGoalUiState.Success -> {
                val goal = uiState.goal
                val errorMessage =
                    uiState.goalValidResult
                        .errorMessage()
                        ?.let { stringResource(id = it) }
                        .orEmpty()

                DetailGoalContent(
                    goal = goal,
                    visibleDialog = visibleDialog,
                    onDismissDialog = { setVisibleDialog(false) },
                    onConfirmDialog = {
                        setVisibleDialog(false)
                        onRemoveGoal(goal.id)
                    },
                    errorMessage = errorMessage,
                    onShowSnackbar = onShowSnackbar,
                    onChangeGoalName = { title -> onChangeGoalName(goal.id, title) },
                    onTogglePinned = { onTogglePinned(goal.id) },
                    onToggleCompleted = { onToggleCompleted(goal.id) },
                    modifier = modifier
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp, bottom = 32.dp),
                )
            }
        }
    }
}

@Composable
private fun DetailGoalContent(
    goal: Goal,
    errorMessage: String?,
    visibleDialog: Boolean,
    onConfirmDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onChangeGoalName: (String) -> Unit,
    onTogglePinned: () -> Unit,
    onToggleCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val header =
        if (goal.isCompleted) {
            stringResource(R.string.feature_detail_goal_complete_editor_header)
        } else {
            stringResource(
                R.string.feature_detail_goal_uncompleted_editor_header,
            )
        }

    GoalEditor(
        modifier = modifier,
        title = goal.title,
        header = header,
        errorMessage = errorMessage,
        onChangeTitle = onChangeGoalName,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            GoalToggleChip(
                text = stringResource(R.string.feature_detail_goal_complete_chip),
                isChecked = goal.isCompleted,
                onCheckedChange = { onToggleCompleted() },
                checkedIcon = ImageVector.vectorResource(DobeDobeIcons.Checked),
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(16.dp))
            GoalToggleChip(
                text = stringResource(R.string.feature_detail_goal_pinned_chip),
                isChecked = goal.isPinned,
                onCheckedChange = {
                    onTogglePinned()
                },
                checkedIcon = ImageVector.vectorResource(DobeDobeIcons.Bookmark),
                modifier = Modifier.weight(1f),
            )
        }
    }

    GoalDeleteDialog(
        visible = visibleDialog,
        onConfirm = onConfirmDialog,
        onDismiss = onDismissDialog,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
    )
}

@Composable
private fun GoalDeleteDialog(
    visible: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!visible) return
    DobeDobeDialog(
        title = stringResource(R.string.feature_detail_goal_delete_dialog_title),
        primaryText = stringResource(R.string.feature_detail_goal_delete_dialog_primary),
        secondaryText = stringResource(R.string.feature_detail_goal_delete_dialog_secondary),
        onClickPrimary = onDismiss,
        onClickSecondary = onConfirm,
        onDismissRequest = onDismiss,
        modifier = modifier,
    )
}

@ThemePreviews
@Composable
private fun DeleteDialogPreview() {
    DobeDobeTheme {
        GoalDeleteDialog(
            visible = true,
            onConfirm = {},
            onDismiss = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        )
    }
}

@ThemePreviews
@Composable
private fun DetailGoalContentPreview() {
    DobeDobeTheme {
        DobeDobeBackground {
            DetailGoalContent(
                goal = Goal.todo("test"),
                visibleDialog = false,
                onConfirmDialog = {},
                onDismissDialog = {},
                errorMessage = null,
                onShowSnackbar = { _, _ -> false },
                onChangeGoalName = {},
                onTogglePinned = {},
                onToggleCompleted = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp, bottom = 32.dp),
            )
        }
    }
}
