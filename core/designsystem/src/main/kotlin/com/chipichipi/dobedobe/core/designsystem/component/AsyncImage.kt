package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

private val DefaultOnError: @Composable () -> Unit = {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
    )
}

private val DefaultOnLoading: @Composable () -> Unit = {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
    )
}

/**
 * TODO : AsyncImage 컴포넌트 단순 Wrapper 임시 처리, 각 상태 디자인 정의 필요
 */
@Composable
fun DobDobeAsyncImage(
    model: Any?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    onError: @Composable () -> Unit = DefaultOnError,
    onLoading: @Composable () -> Unit = DefaultOnLoading,
) {
    var imagePainterState by remember {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }

    val imageLoader =
        rememberAsyncImagePainter(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(model)
                    .crossfade(true)
                    .build(),
            contentScale = contentScale,
            onState = { state -> imagePainterState = state },
        )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (imagePainterState) {
            is AsyncImagePainter.State.Loading -> {
                onLoading()
            }
            is AsyncImagePainter.State.Error -> {
                onError()
            }
            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Success,
            -> Unit
        }

        Image(
            painter = imageLoader,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    }
}
