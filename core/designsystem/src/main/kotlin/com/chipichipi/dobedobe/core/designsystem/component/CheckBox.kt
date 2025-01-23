package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// TODO: CheckBox 컴포넌트 단순 Wrapper 임시 처리, 각 상태 디자인 정의 필요
@Composable
fun DobeDobeCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        modifier = modifier,
    )
}
