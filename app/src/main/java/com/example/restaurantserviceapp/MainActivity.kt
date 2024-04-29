package com.example.restaurantserviceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.restaurantserviceapp.ui.theme.RestaurantServiceAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            RestaurantServiceAppTheme {
                // A surface container using the 'background' color from the theme
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}