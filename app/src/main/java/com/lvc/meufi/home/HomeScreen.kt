package com.lvc.meufi.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.House
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material.icons.outlined.Upgrade
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lvc.meufi.R
import com.lvc.meufi.home.data.DividendPageData
import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.FiiType
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.ui_components.MainIconButton
import com.lvc.meufi.ui.theme.FiiColor
import com.lvc.meufi.ui_components.MonthYearPicker
import com.lvc.meufi.utils.toDateString

@Composable
fun HomeScreenView(
    modifier: Modifier = Modifier,
    loading: Boolean = true,
    dividendPage: DividendPageData,
    onClickDividend: (FiiDividendData) -> Unit,
    onAddClick: () -> Unit,
    selectedMonth: MonthDayYear,
    onSelectMonth: (MonthDayYear) -> Unit,
    months: List<MonthDayYear>,
    onUpdateClick: () -> Unit
) {
    if (loading) {
        LoadingView()
    } else {
        WalletScreen(
            modifier = modifier,
            dividendPageData = dividendPage,
            onClickDividend = onClickDividend,
            onAddClick = onAddClick,
            selectedMonth = selectedMonth,
            onSelectMonth = onSelectMonth,
            months = months,
            onUpdateClick = onUpdateClick
        )
    }
}

@Composable
private fun WalletScreen(
    modifier: Modifier,
    dividendPageData: DividendPageData,
    selectedMonth: MonthDayYear,
    onSelectMonth: (MonthDayYear) -> Unit,
    onClickDividend: (FiiDividendData) -> Unit,
    onAddClick: () -> Unit,
    months: List<MonthDayYear>,
    onUpdateClick: () -> Unit
) {
    val expandDate = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        WalletTitle(
            onAddClick = onAddClick,
            onCalendarClick = {
                expandDate.value = !expandDate.value
            },
            onUpdateClick = onUpdateClick
        )

        AnimatedVisibility(visible = expandDate.value) {
            DatePicker(
                selected = selectedMonth,
                onSelectMonth = onSelectMonth,
                months = months
            )
        }

        DividendsPageView(
            dividendPageData = dividendPageData,
            onClickDividend = onClickDividend
        )
    }
}

@Composable
private fun DatePicker(
    selected: MonthDayYear,
    onSelectMonth: (MonthDayYear) -> Unit,
    months: List<MonthDayYear>
) {
    Spacer(modifier = Modifier.size(4.dp))
    MonthYearPicker(
        selected = selected,
        months = months,
        onSelectMonth = onSelectMonth
    )
    Spacer(modifier = Modifier.size(4.dp))
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator()
            Text(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center)
                    .padding(16.dp),
                text = stringResource(id = R.string.loading_text),
                fontSize = 24.sp,
                style = MaterialTheme.typography.h1
            )
        }
    }
}

@Composable
private fun WalletTitle(
    onAddClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onUpdateClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .padding(16.dp),
            text = stringResource(id = R.string.wallet),
            fontSize = 24.sp,
            style = MaterialTheme.typography.h1
        )

        Row(
            horizontalArrangement = Arrangement.End
        ) {

            MainIconButton(
                icon = Icons.Outlined.Update,
                onClick = onUpdateClick
            )

            MainIconButton(
                icon = Icons.Outlined.CalendarMonth,
                onClick = onCalendarClick
            )

            MainIconButton(
                icon = Icons.Outlined.Add,
                onClick = onAddClick
            )
        }
    }
}

@Composable
fun DividendsPageView(
    dividendPageData: DividendPageData,
    onClickDividend: (FiiDividendData) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        DividendsResume(dividendPageData = dividendPageData)

        Spacer(modifier = Modifier.size(8.dp))

        YourFiis(fiiAmount = dividendPageData.dividendData.size)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        ) {
            items(dividendPageData.dividendData) { item ->
                DividendCard(dividendData = item, onClickDividend = onClickDividend)
            }
        }
    }
}

@Composable
private fun DividendsResume(dividendPageData: DividendPageData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(4.dp),
                text = "R$",
                fontSize = 12.sp,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = dividendPageData.dividendSumToDisplay(),
                fontSize = 36.sp,
                style = MaterialTheme.typography.h1
            )
        }
        Row {
            Text(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(4.dp),
                text = stringResource(id = R.string.dividends_for),
                fontSize = 12.sp,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = dividendPageData.date.toDateString(),
                fontSize = 16.sp,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1
            )
        }
    }
}

@Composable
private fun YourFiis(fiiAmount: Int) {
    Row(
        Modifier.padding(start = 8.dp)
    ) {
        Text(
            modifier = Modifier.wrapContentSize(align = Alignment.Center),
            text = stringResource(id = R.string.your_fiis),
            fontSize = 16.sp,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = Modifier.wrapContentSize(align = Alignment.Center),
            text = "($fiiAmount)",
            fontSize = 16.sp,
            style = MaterialTheme.typography.h2,
            color = FiiColor.Grey700
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DividendCard(
    dividendData: FiiDividendData,
    onClickDividend: (FiiDividendData) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(FiiColor.Grey100, RoundedCornerShape(8.dp))
            .clickable {
                onClickDividend(dividendData)
            }
            .padding(16.dp)
    ) {
        val notFilled = dividendData.type == FiiType.NOT_FILLED
        val bePaidIn = dividendData.daysToBePaid()
        val alreadyPaid = bePaidIn == 0L

        val dividendDateStr =
            when {
                notFilled -> stringResource(id = R.string.no_dividends_informed_yet)
                alreadyPaid -> stringResource(id = R.string.already_paid)
                else -> stringResource(
                    id = R.string.will_be_paid_at,
                    dividendData.daysToBePaid().toString()
                )
            }

        val dividendDateColor =
            when {
                notFilled -> Color.Black
                alreadyPaid -> FiiColor.Green600
                else -> FiiColor.Purple700
            }

        val icon = when (dividendData.type) {
            FiiType.PAPER -> Icons.Outlined.Description
            FiiType.FOUNDS_OF_FOUNDS -> Icons.Outlined.Wallet
            FiiType.BRICK -> Icons.Outlined.House
            FiiType.NOT_FILLED -> Icons.Outlined.Pending
        }

        Row {
            Icon(
                modifier = Modifier
                    .weight(0.2f)
                    .size(32.dp),
                imageVector = icon,
                contentDescription = ""
            )

            Column(modifier = Modifier.weight(0.4f)) {
                Text(
                    text = dividendData.fiiCode,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.h1
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = "R$ ${dividendData.dividendToDisplay()}",
                    fontSize = 14.sp
                )

                dividendData.dividendYieldToDisplay()?.let {
                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = it,
                        fontSize = 14.sp
                    )
                }
            }

            Column(modifier = Modifier.weight(0.4f)) {
                Text(
                    text = pluralStringResource(
                        R.plurals.quotas,
                        dividendData.amount,
                        dividendData.amount.toString()
                    ),
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "R$ ${dividendData.dividendSumToDisplay()}",
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = dividendDateStr,
                    fontSize = 14.sp,
                    color = dividendDateColor,
                    style = MaterialTheme.typography.h1
                )

            }
        }
    }
}