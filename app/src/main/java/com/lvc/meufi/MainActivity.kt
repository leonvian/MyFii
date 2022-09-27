package com.lvc.meufi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lvc.meufi.home.HomeScreenView
import com.lvc.meufi.home.HomeViewModel
import com.lvc.meufi.persistence.FakeData
import com.lvc.meufi.persistence.remote.SkrapeHandler
import com.lvc.meufi.ui.theme.MeuFiTheme
import com.lvc.meufi.utils.FiiColor
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeuFiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().background(color = FiiColor.Grey400),
                    color = MaterialTheme.colors.background,
                ) {
                    HomeScreenView(
                        loading = homeViewModel.loading.value,
                        dividendPages = homeViewModel.dividendPages.value
                    )
                }
            }
        }

        loadData()

      //  SkrapeHandler().skrapDataSafely("hsaf11")
    }

    private fun loadData() {
        homeViewModel.loadFiiDividedPage()
    }
}
