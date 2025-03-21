package com.chipichipi.dobedobe.feature.dashboard.component

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.canhub.cropper.CropImageView
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeDialog
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.feature.dashboard.R
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardModeState
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import com.chipichipi.dobedobe.feature.dashboard.util.bitmapToUri
import com.chipichipi.dobedobe.feature.dashboard.util.deleteDraftFiles
import com.chipichipi.dobedobe.feature.dashboard.util.updateModifiedPhotosToFile
import kotlinx.coroutines.launch

@Composable
internal fun DashboardEditMode(
    modeState: DashboardModeState.Edit,
    onToggleMode: () -> Unit,
    onUpsertPhotos: (List<DashboardPhoto>) -> Unit,
    onDeletePhotos: (List<DashboardPhoto>) -> Unit,
    onUpdatePhotoDrafts: (Int?, Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedPhotoId by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current
    var showCropView by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val cropView = remember(context) {
        CropImageView(context).apply {
            isAutoZoomEnabled = false
            cropShape = CropImageView.CropShape.RECTANGLE
            setFixedAspectRatio(true)
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (selectedPhotoId != null && uri != null) {
                cropView.setImageUriAsync(uri)
                showCropView = true
            }
        },
    )

    val onExitEditMode: () -> Unit = {
        coroutineScope.launch {
            deleteDraftFiles(context)
            onToggleMode()
        }
    }

    BackHandler {
        onExitEditMode()
    }

    DashboardEditModeBody(
        modifier = modifier,
        onToggleMode = onExitEditMode,
        photoDraftsState = modeState.drafts,
        onUpsertPhotos = onUpsertPhotos,
        onDeletePhotos = onDeletePhotos,
        onDeleteDraftsPhoto = { id ->
            onUpdatePhotoDrafts(id, Uri.EMPTY)
        },
        onPickDraftsPhoto = { id ->
            selectedPhotoId = id

            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
            )
        },
        selectedPhotoId = selectedPhotoId,
        onChangeId = { id ->
            selectedPhotoId = id
        },
    )

    if (showCropView) {
        CropViewScreen(
            onCancel = {
                showCropView = false
                selectedPhotoId = null
            },
            onSave = {
                coroutineScope.launch {
                    cropView.getCroppedImage()?.let { croppedImage ->
                        val uri = bitmapToUri(context, croppedImage)
                        if (uri != null && selectedPhotoId != null) {
                            onUpdatePhotoDrafts(selectedPhotoId, uri)
                            showCropView = false
                            selectedPhotoId = null
                        }
                    }
                }
            },
            cropImageView = cropView,
        )
    }
}

@Composable
private fun DashboardEditModeBody(
    onToggleMode: () -> Unit,
    photoDraftsState: List<DashboardPhotoState>,
    onUpsertPhotos: (List<DashboardPhoto>) -> Unit,
    onDeletePhotos: (List<DashboardPhoto>) -> Unit,
    onDeleteDraftsPhoto: (Int) -> Unit,
    onPickDraftsPhoto: (Int) -> Unit,
    selectedPhotoId: Int?,
    onChangeId: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isShowEditOptionDialog by rememberSaveable { mutableStateOf(false) }

    if (isShowEditOptionDialog && selectedPhotoId != null) {
        DashboardEditOptionsDialog(
            onDismissRequest = {
                isShowEditOptionDialog = false
            },
            onPickPhoto = {
                onPickDraftsPhoto(selectedPhotoId)
            },
            onDeletePhoto = {
                onDeleteDraftsPhoto(selectedPhotoId)
            },
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = DobeDobeTheme.colors.black.copy(0.7f),
            ),
    ) {
        DashboardEditModeTopAppBar(
            onToggleMode = onToggleMode,
            onSavePhotos = {
                coroutineScope.launch {
                    val modifiedPhotos = photoDraftsState.filter { it.hasUriChanged }
                    val updatedPhotos = updateModifiedPhotosToFile(context, modifiedPhotos)
                    val (photosToDelete, photosToUpsert) = updatedPhotos.partition { it.path == null }

                    if (photosToUpsert.isNotEmpty()) {
                        onUpsertPhotos(photosToUpsert)
                    }

                    if (photosToDelete.isNotEmpty()) {
                        onDeletePhotos(photosToDelete)
                    }

                    onToggleMode()
                }
            },
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp),
            contentAlignment = Alignment.Center,
        ) {
            photoDraftsState.forEach { photo ->
                EditModePhotoFrame(
                    config = photo.config,
                    uri = photo.uri,
                    rotation = photo.config.rotationZ,
                    onPickPhoto = {
                        onPickDraftsPhoto(photo.config.id)
                    },
                    onDeletePhoto = {
                        onChangeId(photo.config.id)
                        isShowEditOptionDialog = true
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(19.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(DobeDobeIcons.EditMode),
                contentDescription = "edit mode icon",
                tint = Color.Unspecified,
            )

            Text(
                text = stringResource(R.string.feature_dashboard_edit_mode_description),
                style = DobeDobeTheme.typography.body1,
                color = DobeDobeTheme.colors.white,
            )
        }
    }
}

@Composable
private fun DashboardEditOptionsDialog(
    onDismissRequest: () -> Unit,
    onPickPhoto: () -> Unit,
    onDeletePhoto: () -> Unit,
) {
    DobeDobeDialog(
        onDismissRequest = onDismissRequest,
        title = stringResource(R.string.feature_dashboard_edit_change_image_title),
        primaryText = stringResource(R.string.feature_dashboard_edit_select_image_from_album),
        secondaryText = stringResource(R.string.feature_dashboard_edit_delete_image),
        onClickPrimary = {
            onPickPhoto()
            onDismissRequest()
        },
        onClickSecondary = {
            onDeletePhoto()
            onDismissRequest()
        },
    )
}
