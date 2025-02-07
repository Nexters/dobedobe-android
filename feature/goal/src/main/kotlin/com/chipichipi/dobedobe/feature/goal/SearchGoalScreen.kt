package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.goal.component.GoalRow
import com.chipichipi.dobedobe.feature.goal.component.GoalSearchBar
import com.skydoves.cloudy.cloudy
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SearchGoalRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    navigateToGoalDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchGoalViewModel = koinViewModel(),
) {
    val queryState = rememberTextFieldState()
    val goals: List<Goal> = List(10) {
        Goal.todo("Goal $it").copy(id = it.toLong())
    }
    val queriedGoals = goals.take(3)
    val onCancelSearch = {
        queryState.edit {
            delete(0, this.length)
        }
    }

    SearchGoalScreen(
        goals = goals,
        queriedGoals = queriedGoals,
        queryState = queryState,
        onCancelSearch = onCancelSearch,
        onCloseSearch = navigateToBack,
        onClickGoal = navigateToGoalDetail,
        modifier = modifier,
    )
}

@Composable
private fun SearchGoalScreen(
    goals: List<Goal>,
    queriedGoals: List<Goal>,
    queryState: TextFieldState,
    onCancelSearch: () -> Unit,
    onCloseSearch: () -> Unit,
    onClickGoal: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(modifier = modifier) { innerPadding ->
        SearchGoalBackGround {
            Column(
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding)
                    .padding(top = 16.dp),
            ) {
                SearchGoalHeader(
                    isQueried = queriedGoals.isNotEmpty(),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 24.dp),
                )
                SearchGoalList(
                    goals = goals,
                    queriedGoals = queriedGoals,
                    onClickGoal = onClickGoal,
                )
                HorizontalDivider(color = DobeDobeTheme.colors.gray200, thickness = 1.dp)
                GoalSearchBar(
                    queryState = queryState,
                    onCloseSearch = onCloseSearch,
                    onCancelSearch = onCancelSearch,
                    focusRequester = focusRequester,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DobeDobeTheme.colors.white)
                        .padding(start = 20.dp, end = 8.dp)
                        .padding(vertical = 4.dp)
                        .imePadding()
                        .navigationBarsPadding(),
                )
            }
        }
    }
}

@Composable
private fun SearchGoalBackGround(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.rabbit_sheet_content_background),
                contentScale = ContentScale.FillBounds,
            )
            .cloudy(200),
    )
    content()
}

@Composable
private fun SearchGoalHeader(
    isQueried: Boolean,
    modifier: Modifier = Modifier,
) {
    val title = if (isQueried) {
        stringResource(R.string.feature_goal_search_recent_goal_title)
    } else {
        stringResource(R.string.feature_goal_search_queried_goal_title)
    }
    Text(
        text = title,
        style = DobeDobeTheme.typography.heading2,
        color = DobeDobeTheme.colors.gray50,
        modifier = modifier,
    )
}

@Composable
private fun ColumnScope.SearchGoalList(
    goals: List<Goal>,
    queriedGoals: List<Goal>,
    onClickGoal: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
    ) {
        items(
            items = queriedGoals.ifEmpty { goals },
            key = { it.id },
        ) { goal ->
            GoalRow(
                goal = goal,
                enabled = false,
                onClick = { onClickGoal(goal.id) },
            )
        }
    }
}

@ThemePreviews
@Composable
private fun SearchGoalScreenPreview() {
    DobeDobeTheme {
        SearchGoalScreen(
            goals = List(3) {
                Goal.todo("Goal $it").copy(id = it.toLong())
            },
            queriedGoals = List(2) {
                Goal.todo("Goal $it").copy(id = it.toLong())
            },
            queryState = rememberTextFieldState(),
            onCancelSearch = {},
            onCloseSearch = {},
            onClickGoal = {},
        )
    }
}