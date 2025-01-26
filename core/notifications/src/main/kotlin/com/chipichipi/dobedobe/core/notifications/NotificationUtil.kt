package com.chipichipi.dobedobe.core.notifications

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

internal fun handleNotificationToggle(
    context: Context,
    checked: Boolean,
    onNotificationToggled: (Boolean) -> Unit,
) {
    if (checked) {
        if (checkSystemNotificationEnabled(context)) {
            onNotificationToggled(true)
        } else {
            openSystemNotificationSetting(context)
        }
    } else {
        onNotificationToggled(false)
    }
}

internal fun checkSystemNotificationEnabled(context: Context) =
    NotificationManagerCompat
        .from(context)
        .areNotificationsEnabled()

private fun openSystemNotificationSetting(context: Context) {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        .apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }

    context.startActivity(intent)
}
