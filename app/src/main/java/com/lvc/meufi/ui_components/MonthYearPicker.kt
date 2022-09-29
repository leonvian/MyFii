package com.lvc.meufi.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.ui.theme.FiiColor
import com.lvc.meufi.utils.toMonth

@Composable
fun MonthYearPicker(
    selected: MonthDayYear,
    months: List<MonthDayYear>,
    monthYearPickerColors: MonthYearPickerColors = MonthYearPickerColors(),
    onSelectMonth: (MonthDayYear) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(months) { month ->
            MonthCard(
                monthDayYear = month,
                selected = selected == month,
                colors = monthYearPickerColors,
                onSelectMonth = {
                    onSelectMonth(it)
                }
            )
        }
    }
}

@Composable
fun MonthCard(
    monthDayYear: MonthDayYear,
    selected: Boolean,
    colors: MonthYearPickerColors,
    onSelectMonth: (MonthDayYear) -> Unit
) {
    val background = if (selected) colors.selectedBackground else colors.unselectedBackground
    val fontColor = if (selected) colors.selectedTextColor else colors.unselectedTextColor

    Box(
        modifier = Modifier
            .width(150.dp)
            .background(background)
            .clickable {
                onSelectMonth(monthDayYear)
            }
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = monthDayYear.year.toString(),
                fontSize = 16.sp,
                color = fontColor
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = monthDayYear.toMonth(),
                fontSize = 22.sp,
                style = MaterialTheme.typography.h1,
                color = fontColor
            )
        }
    }
}

data class MonthYearPickerColors(
    val selectedTextColor: Color = Color.White,
    val selectedBackground: Color = FiiColor.Purple200,
    val unselectedTextColor: Color = Color.Black,
    val unselectedBackground: Color = Color.White,
)