package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.component.GoalEditor
import com.chipichipi.dobedobe.feature.goal.component.GoalTopAppBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddProductRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    viewModel: AddGoalViewModel = koinViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val goalValidResult by viewModel.goalValidResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToBackEvent
            .onEach { navigateToBack() }
            .flowWithLifecycle(lifecycle)
            .launchIn(this)
    }

    AddGoalScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 32.dp),
        errorMessage = goalValidResult.errorMessage(),
        onShowSnackbar = onShowSnackbar,
        navigateToBack = navigateToBack,
        onChangeGoalName = viewModel::changeGoalTitle,
        onAddGoal = viewModel::addGoal,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
        topBar = {
            GoalTopAppBar(
                navigateToBack = navigateToBack,
                onAddGoal = onAddGoal,
            )
        },
    ) { innerPadding ->
        GoalEditor(
            modifier = modifier.padding(innerPadding),
            title = "",
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
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                errorMessage = null,
                onShowSnackbar = { _, _ -> false },
                navigateToBack = {},
                onChangeGoalName = {},
                onAddGoal = {},
            )
        }
    }
}