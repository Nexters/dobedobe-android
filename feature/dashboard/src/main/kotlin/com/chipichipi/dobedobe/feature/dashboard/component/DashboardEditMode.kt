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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeDialog
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.feature.dashboard.R
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardModeState
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import com.chipichipi.dobedobe.feature.dashboard.util.updateModifiedPhotosToFile
import kotlinx.coroutines.launch

@Composable
internal fun DashboardEditMode(
    modeState: DashboardModeState.Edit,
    onToggleMode: () -> Unit,
    onUpsertPhotos: (List<DashboardPhoto>) -> Unit,
    onDeletePhotos: (List<Int>) -> Unit,
    onUpdatePhotoDrafts: (Int?, Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedPhotoId by remember { mutableStateOf<Int?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (selectedPhotoId != null && uri != null) {
                onUpdatePhotoDrafts(selectedPhotoId, uri)
            }

            selectedPhotoId = null
        },
    )

    BackHandler {
        onToggleMode()
    }

    DashboardEditModeBody(
        modifier = modifier,
        onToggleMode = onToggleMode,
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
}

@Composable
private fun DashboardEditModeBody(
    onToggleMode: () -> Unit,
    photoDraftsState: List<DashboardPhotoState>,
    onUpsertPhotos: (List<DashboardPhoto>) -> Unit,
    onDeletePhotos: (List<Int>) -> Unit,
    onDeleteDraftsPhoto: (Int) -> Unit,
    onPickDraftsPhoto: (Int) -> Unit,
    selectedPhotoId: Int?,
    onChangeId: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isShowEditOptionDialog by remember { mutableStateOf(false) }

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
                        onPickDraftsPhoto(photo.config.id)
                    },
                    onDeletePhoto = {
                        onChangeId(photo.config.id)
                        isShowEditOptionDialog = true
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
private fun DashboardEditOptionsDialog(
    onDismissRequest: () -> Unit,
    onPickPhoto: () -> Unit,
    onDeletePhoto: () -> Unit,
) {
    // TODO : 컬러/string 변경 필요
    DobeDobeDialog(
        onDismissRequest = onDismissRequest,
        title = "이미지 변경",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
    ) {
        Button(
            onClick = {
                onPickPhoto()
                onDismissRequest()
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF262C36),
                contentColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
        ) {
            Text(
                text = "앨범에서 이미지 찾기",
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
            )
        }

        Button(
            onClick = {
                onDeletePhoto()
                onDismissRequest()
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Red,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
        ) {
            Text(
                text = "이미지 삭제",
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
            )
        }
    }
}
