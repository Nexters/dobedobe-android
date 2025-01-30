package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DobeDobeBottomSheetScaffold(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetPeekHeight: Dp = BottomSheetDefaults.SheetPeekHeight,
    sheetDragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
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
            sheetDragHandle = sheetDragHandle,
            topBar = topBar,
            snackbarHost = snackbarHost,
            content = content,
            sheetContainerColor = DobeDobeTheme.colors.white,
            containerColor = DobeDobeTheme.colors.white,
        )
    }
}
