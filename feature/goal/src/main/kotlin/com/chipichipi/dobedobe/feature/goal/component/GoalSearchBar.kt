package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun GoalSearchBar(
    modifier: Modifier = Modifier,
    inputContent: @Composable RowScope.() -> Unit = {},
) {
    Surface(
        modifier = modifier,
        color = DobeDobeTheme.colors.gray100,
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .defaultMinSize(40.dp)
                .padding(start = 12.dp, end = 51.dp)
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = ImageVector.vectorResource(DobeDobeIcons.Search),
                contentDescription = "search Goal",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(8.dp))
            inputContent()
        }
    }
}

@ThemePreviews
@Composable
private fun GoalSearchBarPreview() {
    DobeDobeTheme {
        GoalSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 10.dp),
        ) {
            DobeDobeTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                state = rememberTextFieldState(),
                hint = "목표 검색",
                textStyle = DobeDobeTheme.typography.body1,
                imeAction = ImeAction.Search,
            )
        }
    }
}