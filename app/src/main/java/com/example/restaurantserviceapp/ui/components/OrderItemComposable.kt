package com.example.restaurantserviceapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.restaurantserviceapp.admin.ui.model.Order
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun OrderItemComposable(
    item: Order,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        shape = RoundedCornerShape(
            4.dp
        ),
        border = BorderStroke(2.dp, Color(0xffD2992F)),
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = Color(0xffFCD19C),
                disabledContainerColor = Color(0xffFCD19C)
            ),
        modifier = modifier
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 20.dp)
        ) {
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

                Text(text = formattedDate)

                Spacer(modifier = Modifier.width(4.dp))

                Text(text = item.id)

                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                        .weight(1f)
                )

                Text(text = "Tb${item.table}")

            }

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(text = "Total: ${item.value}")
            }
        }

    }


}