package com.example.restaurantserviceapp.waiter.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantserviceapp.admin.ui.ComposeChart1
import com.example.restaurantserviceapp.ui.components.DateChooseRow
import com.example.restaurantserviceapp.ui.components.StatisticDetailsCard
import com.example.restaurantserviceapp.ui.theme.interFontFamily
import com.example.restaurantserviceapp.waiter.model.WaiterState
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WaiterComposable(
    state: WaiterState,
    onExit: () -> Unit,
    onDateChosen: (Instant) -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Active orders",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = onExit) {
                    Text(
                        "Exit",
                        style = TextStyle(
                            fontFamily = interFontFamily,
                            fontSize = 16.sp
                        )
                    )
                }

            }


            LazyColumn {

            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Statistic",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth()
            )

            DateChooseRow(onDateChosen = onDateChosen)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                StatisticDetailsCard(
                    "Orders",
                    state.numOfOrders.toString(),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(24.dp).weight(1f))

                StatisticDetailsCard(
                    "Tips",
                    state.tips.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            val modelProducer = remember { CartesianChartModelProducer.build() }

            if (state.ordersNumberForChart.isNotEmpty()) {
                val xToDateMapKey = ExtraStore.Key<Map<Float, Instant>>()

                val xToDates = state.ordersNumberForChart.keys.associateBy {
                    it.toLocalDateTime(
                        TimeZone.currentSystemDefault()
                    ).hour.toFloat()
                }

                modelProducer.tryRunTransaction {
                    lineSeries { series(xToDates.keys, state.ordersNumberForChart.values) }
                    updateExtras { it[xToDateMapKey] = xToDates }
                }

                CartesianValueFormatter { x, chartValues, _ ->
                    // Ensure x is treated as epoch milliseconds and properly converted to an Instant
                    val instant = chartValues.model.extraStore[xToDateMapKey][x]
                        ?: Instant.fromEpochMilliseconds(x.toLong()) // Fallback to converting x directly if not found in the map

                    // Use DateTimeFormatter to format the Instant into a readable hour format
                    DateTimeFormatter
                        .ofPattern(
                            "HH:mm",
                            Locale.getDefault()
                        ) // Now includes minutes for clarity, adjust if only hours are needed
                        .format(
                            instant.toJavaInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        )
                }
            }

            ComposeChart1(
                modelProducer
            )

        }
    }
}