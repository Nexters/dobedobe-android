package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

val DRAG_HANDLER_HEIGHT = 24.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DobeDobeBottomSheetScaffold(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetPeekHeight: Dp = BottomSheetDefaults.SheetPeekHeight,
    topBar: @Composable (() -> Unit)? = null,
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    content: @Composable (PaddingValues) -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        BottomSheetScaffold(
            sheetContent = sheetContent,
            scaffoldState = scaffoldState,
            sheetPeekHeight = sheetPeekHeight,
            sheetDragHandle = {
                DobeDobeDragHandle()
            },
            topBar = topBar,
            snackbarHost = snackbarHost,
            content = content,
            sheetContainerColor = DobeDobeTheme.colors.white,
            containerColor = DobeDobeTheme.colors.white,
        )
    }
}

@Composable
private fun DobeDobeDragHandle(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .padding(
                top = 8.dp,
                bottom = 12.dp,
            ),
        color = DobeDobeTheme.colors.gray300,
        shape = RoundedCornerShape(4.dp),
    ) {
        Box(Modifier.size(width = 48.dp, height = 4.dp))
    }
}
