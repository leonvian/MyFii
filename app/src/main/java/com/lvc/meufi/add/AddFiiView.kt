package com.lvc.meufi.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lvc.meufi.R
import com.lvc.meufi.ui.theme.MeuFiTheme

@Preview(showBackground = true)
@Composable
fun Preview() {
    AddFiiView(fiiCode = "", fiiCodeOnValueChange = { }, amount = 0, amountOnValueChange = {}, onSaveClicked = {})
}

@Composable
fun AddFiiView(
    fiiCode: String,
    fiiCodeOnValueChange: (String) -> Unit,
    amount: Int,
    amountOnValueChange: (Int) -> Unit,
    onSaveClicked: () -> Unit
) {
    MeuFiTheme {
        Column(
            Modifier
                .padding(24.dp)
                .height(400.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.fii_code),
                fontSize = 24.sp
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = fiiCode,
                onValueChange = fiiCodeOnValueChange
            )
            Text(
                text = stringResource(id = R.string.amount),
                fontSize = 24.sp
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = amount.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { text ->
                    amountOnValueChange(text.toInt())
                }
            )
            Button(
                onClick = onSaveClicked
            ) {

                Text(text = "SAVE")
            }
        }
    }
}