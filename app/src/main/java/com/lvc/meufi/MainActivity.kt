package com.lvc.meufi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lvc.meufi.add.AddFiiView
import com.lvc.meufi.add.AddFiiViewModel
import com.lvc.meufi.home.HomeScreenView
import com.lvc.meufi.home.HomeViewModel
import com.lvc.meufi.ui.theme.MeuFiTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val addFiiViewModel: AddFiiViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeuFiTheme {
                val bottomSheetState = rememberBottomSheetState(
                    initialValue = BottomSheetValue.Collapsed
                )
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = bottomSheetState
                )
                val coroutineScope = rememberCoroutineScope()

                val backgroundAlpha by animateFloatAsState(
                    if (bottomSheetState.isCollapsed)
                        1.0f
                    else
                        0.4f
                )

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            if (scaffoldState.bottomSheetState.isExpanded) {
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.collapse()
                                }
                            }
                        },
                    color = MaterialTheme.colors.background,
                ) {
                    BottomSheetScaffold(
                        backgroundColor = Color.White,
                        scaffoldState = scaffoldState,
                        sheetPeekHeight = 0.dp,
                        sheetContent = {
                            SheetContent()
                            BackHandler(enabled = bottomSheetState.isExpanded) {
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }
                    ) {
                        HomeScreenView(
                            modifier = Modifier.alpha(backgroundAlpha),
                            loading = homeViewModel.loading.value,
                            dividendPages = homeViewModel.dividendPages.value,
                            onClickDividend = {
                                addFiiViewModel.onFiiCode(it.fiiCode)
                                coroutineScope.launch {
                                    scaffoldState.toggle()
                                }
                            }
                        )
                    }
                }
            }
        }

        loadData()
    }

    private suspend fun BottomSheetScaffoldState.toggle() {
        if (bottomSheetState.isCollapsed) {
            bottomSheetState.expand()
        } else {
            bottomSheetState.collapse()
        }
    }

    @Composable
    private fun SheetContent() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AddFiiView(
                fiiCode = addFiiViewModel.fiiCode.value,
                fiiCodeOnValueChange = {
                    addFiiViewModel.onFiiCode(it)
                },
                amount = addFiiViewModel.fiiAmount.value,
                amountOnValueChange = {
                    addFiiViewModel.onFiiAmount(it)
                },
                onSaveClicked = {
                    addFiiViewModel.onSaveClicked()
                }
            )
        }
    }

    private fun loadData() {
        homeViewModel.loadFiiDividedPage()
    }
}
