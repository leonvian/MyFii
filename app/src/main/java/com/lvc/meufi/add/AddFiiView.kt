package com.lvc.meufi.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lvc.meufi.R
import com.lvc.meufi.ui.theme.MeuFiTheme
import com.lvc.meufi.ui_components.MainButton

@Preview(showBackground = true)
@Composable
fun Preview() {
    AddFiiView(isEditModeOn = false, fiiCode = "", fiiCodeOnValueChange = { }, amount = 0, amountOnValueChange = {}, onSaveClicked = {})
}

@Composable
fun AddFiiView(
    isEditModeOn: Boolean,
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
                .height(300.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                enabled = !isEditModeOn,
                label = { Text(text = stringResource(id = R.string.fii_code)) } ,
                modifier = Modifier.fillMaxWidth(),
                value = fiiCode,
                onValueChange = fiiCodeOnValueChange
            )

            Spacer(modifier = Modifier.size(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.amount)) } ,
                value = amount.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { text ->
                    amountOnValueChange(text.toInt())
                }
            )

            Spacer(modifier = Modifier.size(8.dp))

            MainButton(
                buttonText = stringResource(id = R.string.save),
                onClick = {
                    onSaveClicked()
                }
            )
        }
    }
}