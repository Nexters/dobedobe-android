package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.component.GoalEditor
import com.chipichipi.dobedobe.feature.goal.component.GoalTopAppBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddGoalRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    sendSnackBarEvent: (GoalSnackBarType) -> Unit,
    viewModel: AddGoalViewModel = koinViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val errorMessage =
        viewModel.goalValidResult.addGoalErrorMessage()
            ?.let { stringResource(id = it) }

    LaunchedEffect(Unit) {
        viewModel.addGoalEvent
            .onEach {
                sendSnackBarEvent(GoalSnackBarType.ADD)
                navigateToBack()
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(this)
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    AddGoalScreen(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
            }
            .imePadding(),
        errorMessage = errorMessage,
        focusRequester = focusRequester,
        onShowSnackbar = onShowSnackbar,
        navigateToBack = navigateToBack,
        titleState = viewModel.goalTitle,
        onAddGoal = viewModel::addGoal,
    )
}

@Composable
private fun AddGoalScreen(
    errorMessage: String?,
    titleState: TextFieldState,
    focusRequester: FocusRequester,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    onAddGoal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            GoalTopAppBar(
                title = stringResource(R.string.feature_add_goal_top_bar_title),
                navigateToBack = navigateToBack,
            )
        },
    ) { innerPadding ->
        GoalEditor(
            modifier = Modifier
                .background(DobeDobeTheme.colors.white)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 12.dp),
            titleState = titleState,
            supportMessage = stringResource(id = R.string.feature_detail_goal_editor_support_message),
            buttonText = stringResource(id = R.string.feature_add_goal_action_button),
            errorMessage = errorMessage,
            focusRequester = focusRequester,
            onDone = onAddGoal,
        )
    }
}

@Composable
@Preview
private fun AddGoalScreenPreview() {
    DobeDobeTheme {
        DobeDobeBackground {
            val textFieldState = rememberTextFieldState()
            AddGoalScreen(
                modifier = Modifier
                    .fillMaxSize(),
                titleState = textFieldState,
                errorMessage = null,
                focusRequester = FocusRequester(),
                onShowSnackbar = { _, _ -> false },
                navigateToBack = {},
                onAddGoal = {},
            )
        }
    }
}
