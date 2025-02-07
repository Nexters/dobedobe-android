package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun GoalSearchBar(
    queryState: TextFieldState,
    onCancelSearch: () -> Unit,
    onCloseSearch: () -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier.padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = DobeDobeTheme.colors.gray100,
                    shape = RoundedCornerShape(12.dp)
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
            DobeDobeTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                state = queryState,
                // TODO: StringRes 적용
                hint = "목표 검색",
                textStyle = DobeDobeTheme.typography.body1,
                imeAction = ImeAction.Search,
            )
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onCancelSearch()
                        }
                    },
                imageVector = ImageVector.vectorResource(DobeDobeIcons.Cancel),
                contentDescription = "cancel search",
                tint = Color.Unspecified,
            )
        }
        TextButton(onClick = {
            onCloseSearch()
            focusManager.clearFocus()
        }) {
            Text(
                // TODO : StringRes 적용
                text = "닫기",
                style = DobeDobeTheme.typography.body1,
                color = DobeDobeTheme.colors.gray800,
            )
        }
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
            onCancelSearch = {
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