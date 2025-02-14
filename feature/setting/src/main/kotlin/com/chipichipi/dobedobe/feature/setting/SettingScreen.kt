package com.chipichipi.dobedobe.feature.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.core.notifications.NotificationUtil
import com.chipichipi.dobedobe.core.notifications.NotificationUtil.areNotificationsEnabled
import com.chipichipi.dobedobe.feature.setting.component.SelectCharacterDialog
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
    val settingUiState by viewModel.settingUiState.collectAsStateWithLifecycle()

    SettingScreen(
        modifier = modifier,
        uiState = settingUiState,
        navigateToBack = navigateToBack,
        onNotificationToggled = viewModel::setGoalNotificationEnabled,
        onCharacterChangeCompleted = viewModel::changeCharacterCompleted,
    )
}

@Composable
private fun SettingScreen(
    uiState: SettingUiState,
    navigateToBack: () -> Unit,
    onNotificationToggled: (Boolean) -> Unit,
    onCharacterChangeCompleted: (CharacterType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SettingTopAppBar(
                navigateToBack = navigateToBack,
            )
        },
        containerColor = DobeDobeTheme.colors.gray50,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            when (uiState) {
                is SettingUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                    )
                }
                is SettingUiState.Success -> {
                    SettingBody(
                        isGoalNotificationEnabled = uiState.isGoalNotificationEnabled,
                        onNotificationToggled = onNotificationToggled,
                        characterType = uiState.characterType,
                        onCharacterChangeCompleted = onCharacterChangeCompleted,
                    )
                }
            }
        }
    }

    GoalNotificationEffect(
        onNotificationToggled = onNotificationToggled,
    )
}

@Composable
private fun SettingBody(
    isGoalNotificationEnabled: Boolean,
    onNotificationToggled: (Boolean) -> Unit,
    characterType: CharacterType,
    onCharacterChangeCompleted: (CharacterType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var showSelectCharacterDialog by rememberSaveable { mutableStateOf(false) }

    val handleNotificationToggle: (Boolean) -> Unit = { enabled ->
        NotificationUtil.handleNotificationToggle(
            context = context,
            enabled = enabled,
            onNotificationToggled = onNotificationToggled,
        )
    }

    if (showSelectCharacterDialog) {
        SelectCharacterDialog(
            onDismissRequest = { showSelectCharacterDialog = false },
            characterType = characterType,
            onCharacterChangeCompleted = onCharacterChangeCompleted,
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        SettingRow(
            label = stringResource(R.string.feature_setting_goal_notifications),
            onClick = {
                handleNotificationToggle(!isGoalNotificationEnabled)
            },
        ) {
            DobeDobeSwitch(
                modifier = Modifier.padding(end = 8.dp),
                checked = isGoalNotificationEnabled,
                onCheckedChange = { checked ->
                    handleNotificationToggle(checked)
                },
            )
        }

        SettingRow(
            label = stringResource(R.string.feature_setting_app_feedback),
            onClick = { openPlayStore(context) },
        ) {
            Icon(
                painter = painterResource(DobeDobeIcons.ArrowForward),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }

        SettingRow(
            label = stringResource(R.string.feature_setting_change_character),
            onClick = { showSelectCharacterDialog = true },
        ) {
            Icon(
                painter = painterResource(DobeDobeIcons.ArrowForward),
                contentDescription = null,
                tint = Color.Unspecified,
            )
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
