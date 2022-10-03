package com.lvc.meufi.ui_components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String
) {
    Button(
        modifier = modifier.padding(16.dp),
        onClick = onClick
    ) {
        Text(text = buttonText, fontSize = 16.sp)
    }
}

@Composable
fun MainIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String = ""
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.padding(4.dp),
        content = {
            Icon(
                modifier = modifier.size(24.dp),
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    )
}