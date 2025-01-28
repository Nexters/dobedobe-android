package com.chipichipi.dobedobe.feature.dashboard.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeDialog
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.feature.dashboard.R
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import com.chipichipi.dobedobe.feature.dashboard.util.updateModifiedPhotosToFile
import kotlinx.coroutines.launch

@Composable
internal fun DashboardEditMode(
    photoState: List<DashboardPhotoState>,
    onToggleMode: () -> Unit,
    onUpsertPhotos: (List<DashboardPhoto>) -> Unit,
    onDeletePhotos: (List<Int>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var photoDraftsState by remember { mutableStateOf(photoState) }
    var selectedPhotoId by remember { mutableStateOf<Int?>(null) }
    var selectedPhotoForDeletion by remember { mutableStateOf<Int?>(null) }

    val onChangePhoto: (Uri) -> Unit = { uri ->
        photoDraftsState = photoDraftsState.map { draft ->
            if (draft.config.id == selectedPhotoId) {
                draft.copy(
                    uri = uri,
                    hasUriChanged = true,
                )
            } else {
                draft
            }
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (selectedPhotoId != null && uri != null) {
                onChangePhoto(uri)
            }

            selectedPhotoId = null
        },
    )

    val onDeletePhoto: (Int) -> Unit = { id ->
        photoDraftsState = photoDraftsState.map { draft ->
            if (draft.config.id == id) {
                draft.copy(
                    uri = Uri.EMPTY,
                    hasUriChanged = true,
                )
            } else {
                draft
            }
        }
    }

    val onPickPhoto: (Int) -> Unit = { id ->
        selectedPhotoId = id

        photoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
        )
    }

    selectedPhotoForDeletion?.let { id ->
        PhotoDeletionDialog(
            onDismissRequest = {
                selectedPhotoForDeletion = null
            },
            onPickPhoto = {
                onPickPhoto(id)
            },
            onDeletePhoto = {
                onDeletePhoto(id)
            }
        )
    }

    // TODO : 색상 변경 필요
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.7f)),
    ) {
        DashboardEditModeTopAppBar(
            onToggleMode = onToggleMode,
            onSavePhotos = {
                coroutineScope.launch {
                    val modifiedPhotos = photoDraftsState.filter { it.hasUriChanged }
                    val updatedPhotos = updateModifiedPhotosToFile(context, modifiedPhotos)
                    val (photosToDelete, photosToUpsert) = updatedPhotos.partition { it.uri == null }

                    if (photosToUpsert.isNotEmpty()) {
                        onUpsertPhotos(photosToUpsert)
                    }

                    if (photosToDelete.isNotEmpty()) {
                        onDeletePhotos(photosToDelete.map { it.id })
                    }

                    onToggleMode()
                }
            },
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            photoDraftsState.forEach { photo ->
                EditModePhotoFrame(
                    config = photo.config,
                    uri = photo.uri,
                    rotation = photo.config.rotationZ,
                    onPickPhoto = {
                        onPickPhoto(photo.config.id)
                    },
                    onDeletePhoto = {
                        selectedPhotoForDeletion = photo.config.id
                    },
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 230.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // TODO : 색상 변경 필요
                Icon(
                    painter = painterResource(R.drawable.ic_dashboard_edit_mode),
                    tint = Color.White,
                    contentDescription = "edit mode icon",
                )

                Text(
                    text = stringResource(R.string.feature_dashboard_edit_mode_description),
                    fontSize = 16.sp,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
private fun PhotoDeletionDialog(
    onDismissRequest: () -> Unit,
    onPickPhoto: () -> Unit,
    onDeletePhoto: () -> Unit
) {
    DobeDobeDialog(
        onDismissRequest = onDismissRequest,
        title = "이미지 변경"
    ) {
        Button(
            onClick = {
                onPickPhoto()
                onDismissRequest()
            }
        ) {
            Text(
                text = "앨범에서 이미지 찾기"
            )
        }

        Button(
            onClick = {
                onDeletePhoto()
                onDismissRequest()
            }
        ) {
            Text(
                text = "이미지 삭제"
            )
        }
    }
}
