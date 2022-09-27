package com.lvc.meufi.home

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Month
import java.time.format.TextStyle
import java.util.*
import kotlinx.coroutines.launch

@Composable
fun MonthsViewRow(
    fiss: List<FiiMonth>
) {
    val state = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val selectedIndex = remember { mutableStateOf(0) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        itemsIndexed(fiss) { index, item ->
            MonthViewItem(
                index = index,
                fii = item,
                spotLight = selectedIndex.value == index
            ) {
                selectedIndex.value = index
                coroutineScope.launch {
                    state.animateScrollToItem(index = index, scrollOffset = 0)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MonthViewItem(
    index: Int,
    fii: FiiMonth,
    spotLight: Boolean,
    onClickItem: (Int) -> Unit
) {
    Log.i("[TAG]", "Fii Month ${fii.month} ${fii.year} ${fii.dividends}")

    val interactionSource = remember { MutableInteractionSource() }
    val month = Month.of(fii.month).getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
    val backgroundColor = if (spotLight) Color.White else Color.Red //by animateColorAsState(if (spotLight) Color.White else Color.Red)
    val size = if (spotLight) 200.dp else 150.dp // by animateDpAsState(if (spotLight) 200.dp else 150.dp)
    val fontSize =  if (spotLight) 40.sp else 22.sp

    Box(
        Modifier
            .size(size)
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClickItem.invoke(index)
            }
    ) {
        AnimatedContent(
            targetState = spotLight
        ) {
            Column(
                Modifier.padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.animateEnterExit(
                        enter = scaleIn(),
                        exit = scaleOut()
                    ),
                    text = "$month + $index",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    modifier = Modifier.animateEnterExit(
                        enter = scaleIn(),
                        exit = scaleOut()
                    ),
                    text = fii.dividends.toString(),
                    fontSize = fontSize
                )
            }
        }
    }
}


data class FiiMonth(val month: Int, val year: Int, val dividends: Float)