package com.chipichipi.dobedobe.feature.setting.util

import android.content.Context
import android.content.Intent
import android.net.Uri

// TODO : 주소 확인 필요
internal fun openPlayStore(context: Context) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://play.google.com/store/apps/details?id=com.chipichipi.dobedobe"),
    )

    context.startActivity(intent)
}
