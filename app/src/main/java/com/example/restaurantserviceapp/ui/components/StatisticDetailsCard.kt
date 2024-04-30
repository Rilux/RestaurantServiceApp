package com.example.restaurantserviceapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantserviceapp.ui.theme.interFontFamily

@Composable
fun StatisticDetailsCard(
    title : String,
    value: String,
    modifier: Modifier = Modifier
) {

    OutlinedCard(
        border = BorderStroke(
            4.dp,
            Color(0xff757575)
        ),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(6.dp)
        ) {
            Text(
                text = title, style = TextStyle(
                    fontFamily = interFontFamily,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value, style = TextStyle(
                    fontFamily = interFontFamily,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

}