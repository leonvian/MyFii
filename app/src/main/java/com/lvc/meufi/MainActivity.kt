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
import androidx.compose.material.BottomSheetState
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
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.ui.theme.MeuFiTheme
import com.lvc.meufi.utils.toMonthYearDay
import java.util.Date
import kotlinx.coroutines.CoroutineScope
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
                            SheetContent(
                                bottomSheetState = bottomSheetState,
                                coroutineScope = coroutineScope
                            )
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
                            dividendPage = homeViewModel.dividendPage.value,
                            onClickDividend = {
                                addFiiViewModel.applyFiiToEdit(it)
                                coroutineScope.launch {
                                    scaffoldState.toggle()
                                }
                            },
                            onAddClick = {
                                addFiiViewModel.applyEntryFii()
                                coroutineScope.launch {
                                    scaffoldState.toggle()
                                }
                            },
                            selectedMonth = homeViewModel.selectedMonth.value,
                            onSelectMonth = homeViewModel::onSelectedMonth,
                            months = homeViewModel.months.value,
                            onUpdateClick = homeViewModel::forceReloadDividendPage
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
    private fun SheetContent(
        bottomSheetState: BottomSheetState,
        coroutineScope: CoroutineScope
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AddFiiView(
                isEditModeOn = addFiiViewModel.isEditModeOn.value,
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
                    coroutineScope.launch {
                        bottomSheetState.collapse()
                    }
                    homeViewModel.reloadDividendPage()
                }
            )
        }
    }

    private fun loadData(monthDayYear: MonthDayYear = Date().toMonthYearDay()) {
        homeViewModel.loadFiiDividedPage(monthDayYear,false)
    }
}
