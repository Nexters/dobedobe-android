package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.dashboard.R

@Composable
internal fun DashboardCharacterBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        // TODO : 캐릭터 추가 필요. 임시 png 처리
        Image(
            painter = painterResource(R.drawable.temp_bird),
            contentDescription = "temp image",
        )
    }
}

@ThemePreviews
@Composable
private fun DashboardCharacterBoxPreview() {
    DobeDobeTheme {
        DashboardCharacterBox()
    }
}
