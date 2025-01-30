package com.chipichipi.dobedobe.feature.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeSwitch
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.notifications.NotificationUtil
import com.chipichipi.dobedobe.core.notifications.NotificationUtil.areNotificationsEnabled
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
    val isGoalNotificationEnabled by viewModel.isGoalNotificationEnabled.collectAsStateWithLifecycle()

    SettingScreen(
        modifier = modifier,
        isGoalNotificationEnabled = isGoalNotificationEnabled,
        navigateToBack = navigateToBack,
        onNotificationToggled = viewModel::setGoalNotificationEnabled,
    )
}

@Composable
private fun SettingScreen(
    isGoalNotificationEnabled: Boolean,
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
            isGoalNotificationEnabled = isGoalNotificationEnabled,
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
    isGoalNotificationEnabled: Boolean,
    onNotificationToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
    ) {
        SettingRow(
            label = stringResource(R.string.feature_setting_goal_notifications),
        ) {
            DobeDobeSwitch(
                modifier = Modifier.padding(end = 8.dp),
                checked = isGoalNotificationEnabled,
                onCheckedChange = { checked ->
                    NotificationUtil.handleNotificationToggle(
                        context = context,
                        enabled = checked,
                        onNotificationToggled = onNotificationToggled,
                    )
                },
            )
        }

        SettingRow(
            label = stringResource(R.string.feature_setting_app_feedback),
        ) {
            IconButton(
                modifier = Modifier.size(42.dp),
                onClick = { openPlayStore(context) },
            ) {
                Icon(
                    painter = painterResource(DobeDobeIcons.ArrowForward),
                    contentDescription = null,
                    tint = Color.Unspecified,
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
        mutableStateOf(areNotificationsEnabled(context))
    }

    LifecycleResumeEffect(Unit) {
        val updatedSystemNotificationEnabled = areNotificationsEnabled(context)

        if (systemNotificationEnabled != updatedSystemNotificationEnabled) {
            systemNotificationEnabled = updatedSystemNotificationEnabled
            if (updatedSystemNotificationEnabled) {
                onNotificationToggled(true)
            }
        }
        onPauseOrDispose { }
    }
}
