package com.example.restaurantserviceapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.restaurantserviceapp.admin.ui.MyDatePickerDialog
import com.example.restaurantserviceapp.ui.theme.interFontFamily
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

@Composable
fun DateChooseRow(
    onDateChosen: (Instant) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = {
                onDateChosen(it)
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        TextButton(onClick = { onDateChosen(Clock.System.now()) }) {
            Text(
                text = "Today",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        TextButton(onClick = {
            onDateChosen(
                Clock.System.now().minus(1, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
            )
        }) {
            Text(
                text = "Yesterday",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        TextButton(onClick = { showDatePicker = true }) {
            Text(
                text = "Choose period",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }

}