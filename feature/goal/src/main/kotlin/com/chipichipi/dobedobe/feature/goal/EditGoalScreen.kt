package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.goal.component.GoalEditor
import com.chipichipi.dobedobe.feature.goal.component.GoalTopAppBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun EditGoalRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    sendSnackBarEvent: (GoalSnackBarType) -> Unit,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditGoalViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val focusManager = LocalFocusManager.current
    val errorMessage =
        viewModel.goalValidResult.editGoalErrorMessage()
            ?.let { stringResource(id = it) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent
            .onEach { event ->
                when (event) {
                    EditGoalUiEvent.Change -> sendSnackBarEvent(GoalSnackBarType.EDIT)
                    EditGoalUiEvent.NotChange -> {}
                }
                navigateToBack()
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(this)
    }

    EditGoalScreen(
        uiState = uiState,
        goalTitleDraft = viewModel.goalTitleDraft,
        errorMessage = errorMessage,
        saveGoalTitle = { viewModel.saveGoalTitle() },
        navigateToBack = navigateToBack,
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
            }
            .imePadding(),
    )
}

@Composable
private fun EditGoalScreen(
    uiState: EditGoalUiState,
    goalTitleDraft: TextFieldState,
    errorMessage: String?,
    saveGoalTitle: () -> Unit,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            GoalTopAppBar(
                title = stringResource(R.string.feature_detail_goal_top_bar_edit_title),
                navigateToBack = navigateToBack,
            )
        },
    ) { innerPadding ->
        when (uiState) {
            is EditGoalUiState.Loading, EditGoalUiState.Error -> {
                Box(
                    modifier = modifier.padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            is EditGoalUiState.Success -> {
                val focusRequester = remember { FocusRequester() }

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                GoalEditor(
                    modifier = modifier
                        .background(DobeDobeTheme.colors.white)
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp)
                        .padding(top = 8.dp, bottom = 12.dp),
                    titleState = goalTitleDraft,
                    supportMessage = stringResource(id = R.string.feature_detail_goal_editor_support_message),
                    buttonText = stringResource(id = R.string.feature_add_goal_action_button),
                    errorMessage = errorMessage,
                    focusRequester = focusRequester,
                    onDone = saveGoalTitle,
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun EditGoalScreenPreview() {
    DobeDobeTheme {
        EditGoalScreen(
            uiState = EditGoalUiState.Success(Goal.todo("edit")),
            goalTitleDraft = rememberTextFieldState("edit"),
            errorMessage = null,
            saveGoalTitle = {},
            navigateToBack = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
