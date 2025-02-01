package com.chipichipi.dobedobe.feature.dashboard.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

internal suspend fun updateModifiedPhotosToFile(
    context: Context,
    photoStates: List<DashboardPhotoState>,
): List<DashboardPhoto> {
    return photoStates.map { photo ->
        val prefix = "photo_${photo.config.id}"
        deleteFilesWithPrefix(context, prefix)

        val savedFile = saveFileFromUri(
            context = context,
            uri = photo.uri,
            prefix = prefix,
        )

        DashboardPhoto(
            id = photo.config.id,
            path = savedFile?.absolutePath,
        )
    }
}

private suspend fun saveFileFromUri(context: Context, uri: Uri, prefix: String): File? {
    val fileName = "${prefix}_${UUID.randomUUID()}.jpg"
    val destinationFile = File(context.filesDir, fileName)

    return withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                destinationFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            destinationFile
        } catch (e: Exception) {
            Log.e("FileUtils", "Failed to save file from URI", e)
            null
        }
    }
}

private suspend fun deleteFilesWithPrefix(context: Context, prefix: String) {
    withContext(Dispatchers.IO) {
        val filesDir = context.filesDir

        filesDir.listFiles { file -> file.name.startsWith(prefix) }?.forEach { file ->
            if (!file.delete()) {
                Log.w("FileUtils", "Failed to delete file: ${file.absolutePath}")
            }
        }
    }
}
