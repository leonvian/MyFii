package com.lvc.meufi.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.House
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.lvc.meufi.R
import com.lvc.meufi.home.data.DividendPageData
import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.FiiType
import com.lvc.meufi.utils.FiiColor
import com.lvc.meufi.utils.toDateString

@Composable
fun HomeScreenView(
    loading: Boolean = true,
    dividendPages: List<DividendPageData>
) {
    if (loading) {
        LoadingView()
    } else {
        WalletScreen(dividendPages)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun WalletScreen(dividendPages: List<DividendPageData>) {
    Column {
        WalletTitle()
        HorizontalPager(
            modifier = Modifier
                .background(color = Color.White),
            count = dividendPages.size,
            contentPadding = PaddingValues(horizontal = 16.dp),
            itemSpacing = 8.dp
        ) { page ->
            DividendsPageView(
                dividendPageData = dividendPages[page]
            )
        }
    }
}

@Composable
private fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize()
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
private fun WalletTitle() {
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

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {

            }
        ) {
            Text(text = stringResource(id = R.string.add), fontSize = 16.sp)
        }
    }

}

@Composable
fun DividendsPageView(dividendPageData: DividendPageData) {
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
                DividendCard(dividendData = item)
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
                text = dividendPageData.date.toDateString(),
                fontSize = 12.sp,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = ", ",
                fontSize = 12.sp,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "${dividendPageData.dividendDiffByPreviousMonth}",
                fontSize = 12.sp,
                style = MaterialTheme.typography.h2,
                color = FiiColor.Green600
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
fun DividendCard(dividendData: FiiDividendData) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(FiiColor.Grey100, RoundedCornerShape(8.dp))
            .clickable {

            }
            .padding(16.dp)
    ) {
        val icon = when (dividendData.type) {
            FiiType.PAPER -> Icons.Outlined.Description
            FiiType.FOUNDS_OF_FOUNDS -> Icons.Outlined.Wallet
            FiiType.BRICK -> Icons.Outlined.House
        }

        Row {
            Icon(
                modifier = Modifier
                    .weight(0.2f)
                    .size(24.dp),
                imageVector = icon,
                contentDescription = ""
            )

            Column(modifier = Modifier.weight(0.4f)) {
                Text(
                    text = dividendData.fiiCode,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.h1
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "R$ ${dividendData.dividendToDisplay()}",
                    fontSize = 12.sp
                )
            }

            Column(modifier = Modifier.weight(0.4f)) {
                Text(
                    text = pluralStringResource(
                        R.plurals.quotas,
                        dividendData.amount,
                        dividendData.amount.toString()
                    ),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "R$ ${dividendData.dividendSumToDisplay()}",
                    fontSize = 22.sp
                )
            }
        }
    }
}

