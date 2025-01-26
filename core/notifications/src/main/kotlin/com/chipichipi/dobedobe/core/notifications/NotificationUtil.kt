package com.chipichipi.dobedobe.core.notifications

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

object NotificationUtil {
    fun handleNotificationToggle(
        context: Context,
        checked: Boolean,
        onNotificationToggled: (Boolean) -> Unit,
    ) {
        if (checked) {
            if (areNotificationsEnabled(context)) {
                onNotificationToggled(true)
            } else {
                openSystemNotificationSetting(context)
            }
        } else {
            onNotificationToggled(false)
        }
    }

    fun areNotificationsEnabled(context: Context) =
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
}
