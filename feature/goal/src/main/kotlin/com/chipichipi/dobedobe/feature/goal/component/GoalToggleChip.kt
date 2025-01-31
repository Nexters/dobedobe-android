package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
internal fun GoalToggleChip(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkedIcon: ImageVector,
    unCheckedIcon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = 90.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(DobeDobeTheme.colors.gray100)
            .clickable { onCheckedChange(!isChecked) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = if (isChecked) checkedIcon else unCheckedIcon,
            tint = Color.Unspecified,
            contentDescription = "checked",
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = DobeDobeTheme.typography.heading2,
            color = DobeDobeTheme.colors.gray900,
        )
    }
}

@ThemePreviews
@Composable
private fun DobeDobeToggleChipPreview() {
    DobeDobeTheme {
        val (isChecked, onCheckedChange) = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            GoalToggleChip(
                text = "unChecked",
                isChecked = isChecked,
                onCheckedChange = onCheckedChange,
                checkedIcon = Icons.Default.Pin,
                unCheckedIcon = Icons.Default.CheckBoxOutlineBlank,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(16.dp))
            GoalToggleChip(
                text = "checked",
                isChecked = true,
                onCheckedChange = {},
                checkedIcon = Icons.Default.Pin,
                unCheckedIcon = Icons.Default.CheckBoxOutlineBlank,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
