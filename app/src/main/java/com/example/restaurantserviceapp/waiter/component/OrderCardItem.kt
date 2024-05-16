package com.example.restaurantserviceapp.waiter.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantserviceapp.admin.ui.model.Order
import com.example.restaurantserviceapp.ui.theme.interFontFamily
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun OrderCardItem(item: Order) {

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffAFF4C6)
        ),
        border = BorderStroke(
            4.dp,
            Color(0xff65BF8C)
        )
    ) {

        LazyColumn(Modifier.padding(12.dp)) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    val localDateTime = item.time.toLocalDateTime(TimeZone.currentSystemDefault())

                    // Define the desired format
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    val formattedDate = formatter.format(
                        java.time.LocalDateTime.of(
                            localDateTime.year, localDateTime.monthNumber, localDateTime.dayOfMonth,
                            localDateTime.hour, localDateTime.minute, localDateTime.second
                        )
                    )

                    Text(
                        text = formattedDate, style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = interFontFamily,
                            color = Color(0xff1E1E1E)
                        )
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "#" + item.id, style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = interFontFamily,
                            color = Color(0xff1E1E1E)
                        )
                    )

                    Spacer(
                        modifier = Modifier
                            .width(4.dp)
                            .weight(1f)
                    )

                    Text(
                        text = "Tb${item.table}", style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = interFontFamily,
                            color = Color(0xff1E1E1E)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            itemsIndexed(item.items) { index, item ->
                Text(
                    text = "${index + 1}. $item",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = interFontFamily,
                        color = Color(0xff1E1E1E)
                    )
                )
            }

            item {

                Row {
                    Spacer(modifier = Modifier
                        .widthIn(1.dp)
                        .weight(1f))

                    Column {
                        Text(
                            text = "Total:${item.value}",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = interFontFamily,
                                color = Color(0xff1E1E1E)
                            ),
                        )

                        Text(
                            text = "Tips:${item.tips}",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = interFontFamily,
                                color = Color(0xff1E1E1E)
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderCardItem() {

    OrderCardItem(
        Order()
    )

}