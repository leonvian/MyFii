package com.lvc.meufi.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lvc.meufi.ui.theme.MeuFiTheme
import com.lvc.meufi.add.AddFiiView


@Composable
fun ViewContainer() {
    MeuFiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AddFiiView(
                fiiCode = "",
                fiiCodeOnValueChange = {},
                amount = 0,
                amountOnValueChange = {}
            )
            // Greeting("Android")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeuFiTheme {
        ViewContainer()
    }
}