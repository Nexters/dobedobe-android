package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.component.AddGoalTopAppBar
import com.chipichipi.dobedobe.feature.goal.component.GoalEditor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddGoalRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    viewModel: AddGoalViewModel = koinViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val focusManager = LocalFocusManager.current
    val goalValidResult by viewModel.goalValidResult.collectAsStateWithLifecycle()
    val onBack = {
        focusManager.clearFocus()
        navigateToBack()
    }
    val errorMessage =
        goalValidResult.errorMessage()
            ?.let { stringResource(id = it) }

    LaunchedEffect(Unit) {
        viewModel.navigateToBackEvent
            .onEach { onBack() }
            .flowWithLifecycle(lifecycle)
            .launchIn(this)
    }

    AddGoalScreen(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            },
        errorMessage = errorMessage,
        onShowSnackbar = onShowSnackbar,
        navigateToBack = onBack,
        onChangeGoalName = viewModel::changeGoalTitle,
        onAddGoal = viewModel::addGoal,
    )
}

@Composable
private fun AddGoalScreen(
    errorMessage: String?,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    onChangeGoalName: (String) -> Unit,
    onAddGoal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AddGoalTopAppBar(
                navigateToBack = navigateToBack,
                onAddGoal = onAddGoal,
            )
        },
    ) { innerPadding ->
        GoalEditor(
            modifier = Modifier
                .fillMaxSize()
                .background(DobeDobeTheme.colors.white)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp),
            title = "",
            header = stringResource(id = R.string.feature_detail_goal_todo_editor_header),
            errorMessage = errorMessage,
            onChangeTitle = onChangeGoalName,
        )
    }
}

@Composable
@Preview
private fun AddGoalScreenPreview() {
    DobeDobeTheme {
        DobeDobeBackground {
            AddGoalScreen(
                modifier = Modifier
                    .fillMaxSize(),
                errorMessage = null,
                onShowSnackbar = { _, _ -> false },
                navigateToBack = {},
                onChangeGoalName = {},
                onAddGoal = {},
            )
        }
    }
}
