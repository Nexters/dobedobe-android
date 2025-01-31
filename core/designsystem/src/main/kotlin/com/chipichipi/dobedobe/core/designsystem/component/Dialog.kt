package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun DobeDobeDialog(
    onDismissRequest: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
    ),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            color = DobeDobeTheme.colors.white,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = DobeDobeTheme.typography.heading2,
                    color = DobeDobeTheme.colors.gray900,
                    modifier = Modifier
                        .padding(
                            top = 24.dp,
                            bottom = 32.dp,
                        ),
                )
                content()
            }
        }
    }
}

@Composable
fun DobeDobeDialog(
    title: String,
    primaryText: String,
    secondaryText: String,
    onClickPrimary: () -> Unit,
    onClickSecondary: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
    ),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            color = DobeDobeTheme.colors.white,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = DobeDobeTheme.typography.heading2,
                    color = DobeDobeTheme.colors.black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 24.dp),
                )

                if (description != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        style = DobeDobeTheme.typography.body2,
                        color = DobeDobeTheme.colors.gray900,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                DialogButton(
                    text = primaryText,
                    onClick = onClickPrimary,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DobeDobeTheme.colors.gray900,
                        contentColor = DobeDobeTheme.colors.white,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                DialogButton(
                    text = secondaryText,
                    onClick = onClickSecondary,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DobeDobeTheme.colors.white,
                        contentColor = DobeDobeTheme.colors.red,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun DialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = DobeDobeTheme.colors.gray900,
        contentColor = DobeDobeTheme.colors.white,
    ),
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = colors,
    ) {
        Text(
            text = text,
            style = DobeDobeTheme.typography.heading2,
            textAlign = TextAlign.Center,
        )
    }
}

@ThemePreviews
@Composable
private fun DobeDobeDialogPreview() {
    DobeDobeTheme {
        DobeDobeDialog(
            onDismissRequest = {},
            title = "TEST",
        ) {
            Button(
                onClick = {},
            ) {
                Text(text = "TEST")
            }
        }
    }
}

@ThemePreviews
@Composable
private fun DobeDobeDialogPreview2() {
    DobeDobeTheme {
        DobeDobeDialog(
            title = "목표를 삭제하시겠어요?",
            primaryText = "취소",
            secondaryText = "삭제",
            onClickPrimary = {},
            onClickSecondary = {},
            onDismissRequest = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        )
    }
}

@ThemePreviews
@Composable
private fun DobeDobeDialogExistDescriptionPreview() {
    DobeDobeTheme {
        DobeDobeDialog(
            title = "앱 밖에서도 목표를 \n 알려드릴까요?",
            description = "기기 설정에서 알림을 허용해주세요",
            primaryText = "설정하러 가기",
            secondaryText = "닫기",
            onClickPrimary = {},
            onClickSecondary = {},
            onDismissRequest = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        )
    }
}
