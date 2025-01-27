package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeCheckBox
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

// TODO: Color, Text Font 지정 필요
@Composable
internal fun GoalToggleChip(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkedIcon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF3F4F6))
            .clickable { onCheckedChange(!isChecked) }
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isChecked) {
            // TODO : 아이콘 변경
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = checkedIcon,
                contentDescription = "checked",
            )
        }
        if (isChecked.not()) {
            DobeDobeCheckBox(
                modifier = Modifier.size(24.dp),
                checked = false,
                enabled = false,
            )
        }

        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
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
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(16.dp))
            GoalToggleChip(
                text = "checked",
                isChecked = true,
                onCheckedChange = {},
                checkedIcon = Icons.Default.Pin,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
