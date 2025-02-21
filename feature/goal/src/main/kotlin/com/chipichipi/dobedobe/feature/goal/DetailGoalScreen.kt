package com.chipichipi.dobedobe.feature.goal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.chipichipi.dobedobe.feature.goal.component.GoalCompleteDialog
import com.chipichipi.dobedobe.feature.goal.component.GoalToggleChip
import com.chipichipi.dobedobe.feature.goal.component.GoalTopAppBar
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DetailGoalRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    sendSnackBarEvent: (GoalSnackBarType) -> Unit,
    navigateToBack: () -> Unit,
    navigateToEditMode: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailGoalViewModel = koinViewModel(),
) {
    val uiState: DetailGoalUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val (visibleCompleteDialog, setVisibleCompleteDialog) = rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val onBack = {
        if (viewModel.isGoalChanged) {
            sendSnackBarEvent(GoalSnackBarType.EDIT)
        }
        navigateToBack()
    }

    BackHandler {
        onBack()
    }

    DetailGoalEventEffect(
        viewModel = viewModel,
        onShowCompleteDialog = { setVisibleCompleteDialog(true) },
        sendSnackBarEvent = sendSnackBarEvent,
        navigateToBack = navigateToBack,
        onShowSnackbar = onShowSnackbar,
    )

    DetailGoalScreen(
        uiState = uiState,
        visibleCompleteDialog = visibleCompleteDialog,
        onDismissCompleteDialog = { setVisibleCompleteDialog(false) },
        onShowSnackbar = onShowSnackbar,
        navigateToBack = onBack,
        navigateToEditMode = navigateToEditMode,
        onTogglePinned = viewModel::togglePinned,
        onToggleCompleted = viewModel::toggleCompleted,
        onRemoveGoal = viewModel::removeGoal,
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
            },
    )
}

@Composable
private fun DetailGoalEventEffect(
    viewModel: DetailGoalViewModel,
    onShowCompleteDialog: () -> Unit,
    sendSnackBarEvent: (GoalSnackBarType) -> Unit,
    navigateToBack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val coroutineScope = rememberCoroutineScope()
    val snackBarMsg = stringResource(R.string.feature_detail_goal_snackbar_message)

    LaunchedEffect(Unit) {
        viewModel.goalUiEvent
            .onEach { event ->
                when (event) {
                    is DetailGoalUiEvent.UnDoGoal -> {
                        coroutineScope.launch {
                            onShowSnackbar(
                                snackBarMsg,
                                null,
                            )
                        }
                    }

                    is DetailGoalUiEvent.CompleteGoal -> {
                        onShowCompleteDialog()
                        coroutineScope.coroutineContext.cancelChildren()
                    }

                    is DetailGoalUiEvent.Delete -> {
                        sendSnackBarEvent(GoalSnackBarType.DELETE)
                        navigateToBack()
                    }
                }
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(this)
    }
}

@Composable
private fun DetailGoalScreen(
    uiState: DetailGoalUiState,
    visibleCompleteDialog: Boolean,
    onDismissCompleteDialog: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    navigateToEditMode: () -> Unit,
    onTogglePinned: (Long) -> Unit,
    onToggleCompleted: (Long) -> Unit,
    onRemoveGoal: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (visibleDeleteDialog, setVisibleDeleteDialog) = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            GoalTopAppBar(
                title = stringResource(R.string.feature_detail_goal_top_bar_title),
                navigateToBack = navigateToBack,
                actions = {
                    TextButton(
                        onClick = { if (uiState.isSuccess) setVisibleDeleteDialog(true) },
                        colors = ButtonDefaults.textButtonColors().copy(
                            contentColor = DobeDobeTheme.colors.red,
                        ),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.feature_detail_goal_top_bar_remove),
                            style = DobeDobeTheme.typography.body1,
                        )
                    }
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
                DetailGoalContent(
                    goal = goal,
                    onShowSnackbar = onShowSnackbar,
                    onTogglePinned = { onTogglePinned(goal.id) },
                    onToggleCompleted = { onToggleCompleted(goal.id) },
                    onClickEdit = navigateToEditMode,
                    modifier = modifier
                        .fillMaxSize()
                        .background(DobeDobeTheme.colors.white)
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp, bottom = 32.dp),
                )

                GoalDeleteDialog(
                    visible = visibleDeleteDialog,
                    onConfirm = {
                        setVisibleDeleteDialog(false)
                        onRemoveGoal(goal.id)
                    },
                    onDismiss = { setVisibleDeleteDialog(false) },
                )

                GoalCompleteDialog(
                    visible = visibleCompleteDialog,
                    onDismissRequest = onDismissCompleteDialog,
                    characterType = uiState.characterType,
                )
            }
        }
    }
}

@Composable
private fun DetailGoalContent(
    goal: Goal,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onTogglePinned: () -> Unit,
    onToggleCompleted: () -> Unit,
    onClickEdit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(top = 36.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        DetailGoalHeader(isCompleted = goal.isCompleted)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = goal.title,
            style = DobeDobeTheme.typography.title1,
            color = DobeDobeTheme.colors.gray900,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClickEdit,
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = onClickEdit,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = DobeDobeTheme.colors.gray100,
                contentColor = DobeDobeTheme.colors.gray700,
            ),
            contentPadding = PaddingValues(vertical = 9.dp, horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.feature_detail_goal_edit_button_title),
                style = DobeDobeTheme.typography.heading2,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        GoalToggleChipGroup(
            isPinned = goal.isPinned,
            isCompleted = goal.isCompleted,
            onToggleCompleted = onToggleCompleted,
            onTogglePinned = onTogglePinned,
        )
    }
}

@Composable
private fun DetailGoalHeader(isCompleted: Boolean) {
    val title = if (isCompleted) {
        stringResource(R.string.feature_detail_goal_complete_editor_header)
    } else {
        stringResource(
            R.string.feature_detail_goal_uncompleted_editor_header,
        )
    }
    val iconVector = if (isCompleted) {
        ImageVector.vectorResource(DobeDobeIcons.Party)
    } else {
        ImageVector.vectorResource(DobeDobeIcons.Fire)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Icon(
            imageVector = iconVector,
            tint = Color.Unspecified,
            contentDescription = "current goal status",
        )

        Text(
            text = title,
            color = DobeDobeTheme.colors.gray400,
            style = DobeDobeTheme.typography.heading1,
        )
    }
}

@Composable
private fun GoalToggleChipGroup(
    isPinned: Boolean,
    isCompleted: Boolean,
    onToggleCompleted: () -> Unit,
    onTogglePinned: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GoalToggleChip(
            text = stringResource(R.string.feature_detail_goal_complete_chip),
            isChecked = isCompleted,
            onCheckedChange = { onToggleCompleted() },
            checkedIcon = ImageVector.vectorResource(DobeDobeIcons.FlagFilled),
            unCheckedIcon = ImageVector.vectorResource(DobeDobeIcons.FlagOutLine),
            modifier = Modifier.weight(1f),
        )

        GoalToggleChip(
            text = stringResource(R.string.feature_detail_goal_pinned_chip),
            isChecked = isPinned,
            onCheckedChange = {
                onTogglePinned()
            },
            checkedIcon = ImageVector.vectorResource(DobeDobeIcons.PinnedFilled),
            unCheckedIcon = ImageVector.vectorResource(DobeDobeIcons.PinnedOutLine),
            modifier = Modifier.weight(1f),
        )
    }
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
                onShowSnackbar = { _, _ -> false },
                onTogglePinned = {},
                onToggleCompleted = {},
                onClickEdit = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp, bottom = 32.dp),
            )
        }
    }
}
