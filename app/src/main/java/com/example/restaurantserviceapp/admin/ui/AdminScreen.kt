package com.example.restaurantserviceapp.admin.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.restaurantserviceapp.admin.ui.model.AdminIntent
import com.example.restaurantserviceapp.admin.ui.model.AdminSideEffect
import com.example.restaurantserviceapp.admin.ui.model.AdminState
import com.example.restaurantserviceapp.ui.components.OrderItemComposable
import com.example.restaurantserviceapp.ui.components.StatisticDetailsCard
import com.example.restaurantserviceapp.ui.theme.interFontFamily
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.VicoTheme
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toLocalDateTime
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
@Destination
fun AdminScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = hiltViewModel<AdminViewModel>()
    val state by viewModel.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.handleIntent(AdminIntent.OnLoadData)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    if (state.isLoading) {
        Scaffold {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    } else {
        AdminComposable(
            state,
            { viewModel.handleIntent(AdminIntent.OnTodayChosen) },
            { viewModel.handleIntent(AdminIntent.OnYesterdayChosen) },
            { showDatePicker = true }
        )
    }

    viewModel.collectSideEffect { effect ->
        when (effect) {
            AdminSideEffect.ShowDateChooseDialog -> {

            }
        }
    }

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = {
                viewModel.handleIntent(AdminIntent.OnDateChoosen(it))
            },
            onDismiss = { showDatePicker = false }
        )
    }
}


@Composable
private fun AdminComposable(
    state: AdminState,
    onTodayClicked: () -> Unit,
    onYesterdayClicked: () -> Unit,
    onChooseDateClicked: () -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistic",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                        .weight(1f)
                )

                Text(
                    text = "Exit",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(44.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TextButton(onClick = { onTodayClicked() }) {
                    Text(
                        text = "Today",
                        style = TextStyle(
                            fontFamily = interFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                TextButton(onClick = { onYesterdayClicked() }) {
                    Text(
                        text = "Yesterday",
                        style = TextStyle(
                            fontFamily = interFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                TextButton(onClick = { onChooseDateClicked() }) {
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

                Spacer(modifier = Modifier.width(24.dp))

                StatisticDetailsCard(
                    "Income",
                    state.income.toString(),
                    modifier = Modifier.weight(1f)

                )

                Spacer(modifier = Modifier.width(24.dp))

                StatisticDetailsCard(
                    "Tips",
                    state.tips.toString(),
                    modifier = Modifier.weight(1f)

                )
            }

            Spacer(modifier = Modifier.height(50.dp))

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

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "List",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn {
                items(items = state.ordersForList) {
                    Column {
                        OrderItemComposable(
                            it
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}


@Composable
private fun ComposeChart1(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {
    ProvideVicoTheme(rememberM3VicoTheme(lineCartesianLayerColors = listOf(Color(0xffb8dec3)))) {
        CartesianChartHost(
            rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
            ),
            modelProducer,
            modifier = modifier
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (Instant) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        Instant.fromEpochMilliseconds(it)
    } ?: Clock.System.now()

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}