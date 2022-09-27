package com.lvc.meufi.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lvc.meufi.ui.theme.MeuFiTheme

@Preview(showBackground = true)
@Composable
fun Preview() {
    AddFiiView(fiiCode = "", fiiCodeOnValueChange = { }, amount = 0, amountOnValueChange = {})
}

@Composable
fun AddFiiView(
    fiiCode: String,
    fiiCodeOnValueChange: (String) -> Unit,
    amount: Int,
    amountOnValueChange: (Int) -> Unit
) {
    MeuFiTheme {
        Column(
            Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Fii Code",
                fontSize = 24.sp
            )
            TextField(
                value = fiiCode,
                onValueChange = fiiCodeOnValueChange,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Text(
                text = "Amount",
                fontSize = 24.sp
            )
            TextField(
                value = amount.toString(),
                onValueChange = { text ->
                    amountOnValueChange(text.toInt())
                }
            )
            Button(
                onClick = {
                    /*TODO*/
                }) {

                Text(text = "SAVE")
            }
        }
    }
}