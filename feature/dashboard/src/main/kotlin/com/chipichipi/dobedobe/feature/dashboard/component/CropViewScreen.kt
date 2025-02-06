package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.updateLayoutParams
import com.canhub.cropper.CropImageView
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.dashboard.R

@Composable
internal fun CropViewScreen(
    onCancel: () -> Unit,
    onSave: () -> Unit,
    cropImageView: CropImageView,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DobeDobeTheme.colors.black)
            .systemBarsPadding()
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
        ) {
            val (width, height) = with(LocalDensity.current) {
                maxWidth.roundToPx() to maxHeight.roundToPx()
            }

            AndroidView(
                factory = { cropImageView },
            ) { view ->
                view.updateLayoutParams {
                    this.height = height
                    this.width = width
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        ActionBar(
            onCancel = onCancel,
            onSave = onSave,
        )
    }
}

@Composable
private fun ActionBar(
    onCancel: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ActionTextButton(
            text = stringResource(R.string.feature_dashboard_edit_mode_cancel),
            onClick = onCancel
        )
        ActionTextButton(
            text = stringResource(R.string.feature_dashboard_edit_mode_confirm),
            onClick = onSave
        )
    }
}

@Composable
private fun ActionTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val defaultPadding = ButtonDefaults.TextButtonContentPadding

    TextButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 24.dp,
            end = 24.dp,
            top = defaultPadding.calculateTopPadding(),
            bottom = defaultPadding.calculateBottomPadding(),
        ),
    ) {
        Text(
            text = text,
            style = DobeDobeTheme.typography.body1,
            color = DobeDobeTheme.colors.white,
        )
    }
}

@ThemePreviews
@Composable
private fun CropViewScreenPreview() {
    DobeDobeTheme {
        val context = LocalContext.current
        val cropView = remember {
            CropImageView(context).apply {
                isAutoZoomEnabled = false
                cropShape = CropImageView.CropShape.RECTANGLE
                setFixedAspectRatio(true)
            }
        }

        CropViewScreen(
            onCancel = {},
            onSave = {},
            cropImageView = cropView,
        )
    }
}
