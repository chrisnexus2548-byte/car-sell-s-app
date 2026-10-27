package com.opera.carsellsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.opera.carsellsapp.navigations.AppNavHost
import com.opera.carsellsapp.ui.theme.CarSellsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarSellsAppTheme {
                AppNavHost()
            }
        }
    }
}
