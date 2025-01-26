package com.chipichipi.dobedobe.feature.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeSwitch
import com.chipichipi.dobedobe.core.notifications.NotificationUtil
import com.chipichipi.dobedobe.core.notifications.NotificationUtil.checkSystemNotificationEnabled
import com.chipichipi.dobedobe.feature.setting.component.SettingRow
import com.chipichipi.dobedobe.feature.setting.component.SettingTopAppBar
import com.chipichipi.dobedobe.feature.setting.util.openPlayStore
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = koinViewModel(),
) {
    val isGoalNotificationChecked by viewModel.isGoalNotificationChecked.collectAsStateWithLifecycle()

    SettingScreen(
        modifier = modifier,
        isGoalNotificationChecked = isGoalNotificationChecked,
        navigateToBack = navigateToBack,
        onNotificationToggled = viewModel::setGoalNotificationChecked,
    )
}

@Composable
private fun SettingScreen(
    isGoalNotificationChecked: Boolean,
    navigateToBack: () -> Unit,
    onNotificationToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SettingTopAppBar(
                navigateToBack = navigateToBack,
            )
        },
    ) { innerPadding ->
        SettingBody(
            isGoalNotificationChecked = isGoalNotificationChecked,
            onNotificationToggled = onNotificationToggled,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }

    GoalNotificationEffect(
        onNotificationToggled = onNotificationToggled,
    )
}

@Composable
private fun SettingBody(
    isGoalNotificationChecked: Boolean,
    onNotificationToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
    ) {
        // TODO : 언어 대응 필요
        SettingRow(
            label = "알림",
        ) {
            Box(
                modifier = Modifier.padding(end = 8.dp),
            ) {
                DobeDobeSwitch(
                    checked = isGoalNotificationChecked,
                    onCheckedChange = { checked ->
                        NotificationUtil.handleNotificationToggle(
                            context = context,
                            checked = checked,
                            onNotificationToggled = onNotificationToggled,
                        )
                    },
                )
            }
        }

        // TODO : 언어 대응 필요
        SettingRow(
            label = "앱 피드백 남기기",
        ) {
            IconButton(
                modifier = Modifier.size(42.dp),
                onClick = { openPlayStore(context) },
            ) {
                // TODO: 아이콘 변경 필요
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun GoalNotificationEffect(
    onNotificationToggled: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    var systemNotificationEnabled by remember {
        mutableStateOf(checkSystemNotificationEnabled(context))
    }

    LifecycleResumeEffect(Unit) {
        val updatedSystemNotificationEnabled = checkSystemNotificationEnabled(context)

        if (systemNotificationEnabled != updatedSystemNotificationEnabled) {
            systemNotificationEnabled = updatedSystemNotificationEnabled
            if (updatedSystemNotificationEnabled) {
                onNotificationToggled(true)
            }
        }
        onPauseOrDispose { }
    }
}
