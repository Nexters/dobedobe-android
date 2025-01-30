package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTopAppBar
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailGoalTopAppBar(
    navigateToBack: () -> Unit,
    onRemoveGoal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DobeDobeTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = navigateToBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        actions = {
            TextButton(
                onClick = onRemoveGoal,
                colors = ButtonDefaults.textButtonColors().copy(
                    // TODO : 컬러 변경 필요
                    contentColor = Color(0xFFFF354D),
                ),
            ) {
                Text(
                    // TODO: string Resource 로 변경 필요
                    text = "삭제",
                    // TODO: font 변경 필요
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                )
            }
        },
    )
}

@ThemePreviews
@Composable
private fun GoalTopAppBarPreview() {
    DobeDobeTheme {
        DetailGoalTopAppBar(
            navigateToBack = {},
            onRemoveGoal = {},
        )
    }
}
