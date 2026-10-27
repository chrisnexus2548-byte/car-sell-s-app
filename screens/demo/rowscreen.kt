package com.opera.chrisapp.screens.demo

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import org.w3c.dom.Text

@Composable
fun firstscreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        //text
        Text(text = " welcome to my app",
            color = Color.Black,
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif)

    }
}
