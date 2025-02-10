package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.R

@Composable
fun GoalSearchBar(
    modifier: Modifier = Modifier,
    queryState: TextFieldState = rememberTextFieldState(),
    enabled: Boolean = true,
    onClearSearch: (() -> Unit)? = null,
    onCloseSearch: (() -> Unit)? = null,
    onTapSearchBar: (() -> Unit)? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    Row(
        modifier = modifier.padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = DobeDobeTheme.colors.gray100,
                    shape = RoundedCornerShape(12.dp),
                )
                .defaultMinSize(40.dp)
                .padding(start = 12.dp, end = 10.dp)
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = ImageVector.vectorResource(DobeDobeIcons.Search),
                contentDescription = "search Goal",
                tint = Color.Unspecified,
            )
            SearchBarInnerText(
                queryState = queryState,
                enabled = enabled,
                onTapSearchBar = onTapSearchBar,
                focusRequester = focusRequester,
            )
            if (enabled) {
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            onClearSearch?.invoke()

                        },
                    imageVector = ImageVector.vectorResource(DobeDobeIcons.Cancel),
                    contentDescription = "cancel search",
                    tint = Color.Unspecified,
                )
            }
        }
        if (enabled) {
            TextButton(
                onClick = {
                    onCloseSearch?.invoke()
                },
            ) {
                Text(
                    text = stringResource(R.string.feature_goal_search_bar_cancel_button),
                    style = DobeDobeTheme.typography.body1,
                    color = DobeDobeTheme.colors.gray800,
                )
            }
        }
    }
}

@Composable
private fun RowScope.SearchBarInnerText(
    queryState: TextFieldState,
    enabled: Boolean,
    onTapSearchBar: (() -> Unit)? = null,
    focusRequester: FocusRequester,
) {
    if (enabled) {
        DobeDobeTextField(
            state = queryState,
            hint = stringResource(R.string.feature_goal_search_bar_hint),
            textStyle = DobeDobeTheme.typography.body1.copy(
                color = DobeDobeTheme.colors.gray500,
            ),
            imeAction = ImeAction.Search,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
        )
    } else {
        Text(
            text = stringResource(R.string.feature_goal_search_bar_hint),
            style = DobeDobeTheme.typography.body1,
            color = DobeDobeTheme.colors.gray500,
            modifier = Modifier
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures {
                        onTapSearchBar?.invoke()
                    }
                },
        )
    }
}

@ThemePreviews
@Composable
private fun GoalSearchBarPreview() {
    DobeDobeTheme {
        val queryState = rememberTextFieldState()
        GoalSearchBar(
            queryState = queryState,
            onCloseSearch = {},
            onClearSearch = {
                queryState.edit {
                    delete(0, this.length)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        )
    }
}
