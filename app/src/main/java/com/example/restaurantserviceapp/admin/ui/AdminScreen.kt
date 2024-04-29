package com.example.restaurantserviceapp.admin.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.restaurantserviceapp.admin.ui.model.AdminIntent
import com.example.restaurantserviceapp.admin.ui.model.AdminState
import com.example.restaurantserviceapp.ui.theme.interFontFamily
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@Destination
fun AdminScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = hiltViewModel<AdminViewModel>()
    val state = viewModel.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.handleIntent(AdminIntent.OnLoadData)
    }


}


@Composable
private fun AdminComposable(
    state: AdminState,
) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            Row {
                Text(
                    text = "Statistic",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Exit",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(44.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Today",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Text(
                    text = "Yesterday",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Text(
                    text = "Choose period",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "List",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(30.dp))


        }
    }


}




