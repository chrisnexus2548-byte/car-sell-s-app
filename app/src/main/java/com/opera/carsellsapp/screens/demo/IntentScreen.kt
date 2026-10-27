package com.opera.carsellsapp.screens.demo

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntentScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("CARSELL INTENTS", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F172A))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "System Actions & Communication Intents",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF60A5FA)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Intent Action 1: Call Support / Dealer
            IntentActionButton(
                icon = Icons.Default.Phone,
                title = "Call CarSell Support / Seller",
                subtitle = "DIAL Intent to 0714348235",
                buttonColor = Color(0xFF2563EB)
            ) {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0714348235"))
                context.startActivity(dialIntent)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Intent Action 2: Send SMS Inquiry
            IntentActionButton(
                icon = Icons.Default.Sms,
                title = "Send SMS Inquiry to Seller",
                subtitle = "SENDTO Intent with prefilled car details",
                buttonColor = Color(0xFF10B981)
            ) {
                val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:0729618566"))
                smsIntent.putExtra("sms_body", "Hello, I am interested in your car listed on CarSell. Is it available?")
                context.startActivity(smsIntent)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Intent Action 3: Email Support
            IntentActionButton(
                icon = Icons.Default.Email,
                title = "Email CarSell Official Support",
                subtitle = "SENDTO mailto:sales@carsell.com",
                buttonColor = Color(0xFF8B5CF6)
            ) {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", "sales@carsell.com", null)
                )
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CarSell Vehicle Inquiry")
                context.startActivity(emailIntent)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Intent Action 4: Open Web Dealer Portal
            IntentActionButton(
                icon = Icons.Default.Language,
                title = "Visit CarSell Online Web Portal",
                subtitle = "VIEW Intent to website",
                buttonColor = Color(0xFF0284C7)
            ) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.carsell.com"))
                context.startActivity(browserIntent)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Intent Action 5: Open Maps Dealership Location
            IntentActionButton(
                icon = Icons.Default.Map,
                title = "Locate Showrooms on Maps",
                subtitle = "VIEW geo intent for Car Dealerships",
                buttonColor = Color(0xFFD97706)
            ) {
                val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Car+Dealerships+Nairobi"))
                context.startActivity(mapIntent)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Intent Action 6: Camera Capture
            IntentActionButton(
                icon = Icons.Default.CameraAlt,
                title = "Capture Car Photos for Listing",
                subtitle = "ACTION_IMAGE_CAPTURE camera intent",
                buttonColor = Color(0xFFEC4899)
            ) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                context.startActivity(cameraIntent)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Intent Action 7: M-Pesa SIM Toolkit
            IntentActionButton(
                icon = Icons.Default.Payment,
                title = "Launch M-Pesa STK Toolkit",
                subtitle = "Package Launch Intent for SIM Toolkit",
                buttonColor = Color(0xFF059669)
            ) {
                val stkIntent = context.packageManager.getLaunchIntentForPackage("com.android.stk")
                if (stkIntent != null) {
                    context.startActivity(stkIntent)
                }
            }
        }
    }
}

@Composable
fun IntentActionButton(
    icon: ImageVector,
    title: String,
    subtitle: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntentScreenPreview() {
    IntentScreen()
}
