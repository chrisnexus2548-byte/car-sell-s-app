package com.opera.carsellsapp.screens.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TimeToLeave
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.opera.carsellsapp.navigations.ROUTE_PRODUCTLIST

@Composable
fun RowScreen(navController: NavHostController = rememberNavController()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CARSELL CATEGORIES & SHOWCASE",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "Explore vehicle types and dealer recommendations in interactive rows",
            fontSize = 13.sp,
            color = Color(0xFF94A3B8)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Row Section 1: Vehicle Categories
        Text(
            text = "Vehicle Categories",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF60A5FA)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryCard(title = "SUVs & 4x4", icon = Icons.Default.TimeToLeave, count = "140+ Cars") {
                navController.navigate(ROUTE_PRODUCTLIST)
            }
            CategoryCard(title = "Electric / Hybrid", icon = Icons.Default.ElectricCar, count = "65+ Cars") {
                navController.navigate(ROUTE_PRODUCTLIST)
            }
            CategoryCard(title = "Sedans & Saloons", icon = Icons.Default.TimeToLeave, count = "210+ Cars") {
                navController.navigate(ROUTE_PRODUCTLIST)
            }
            CategoryCard(title = "Commercial & Trucks", icon = Icons.Default.TimeToLeave, count = "85+ Cars") {
                navController.navigate(ROUTE_PRODUCTLIST)
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Row Section 2: Budget Filters
        Text(
            text = "Browse By Price Range",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF10B981)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PriceFilterChip(range = "Under Ksh 1M", subtitle = "Budget friendly")
            PriceFilterChip(range = "Ksh 1M - 3M", subtitle = "Popular sedans & hatchbacks")
            PriceFilterChip(range = "Ksh 3M - 7M", subtitle = "Midsize SUVs & luxury")
            PriceFilterChip(range = "Ksh 7M+", subtitle = "Premium & high-end")
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Row Section 3: Verified Dealers
        Text(
            text = "Top Verified Sellers",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF59E0B)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DealerCard(name = "Nairobi Auto Motors", rating = "4.9 ★", listings = "45 active cars")
            DealerCard(name = "Mombasa Prestige Motors", rating = "4.8 ★", listings = "32 active cars")
            DealerCard(name = "Rift Valley Car Hub", rating = "4.9 ★", listings = "28 active cars")
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    icon: ImageVector,
    count: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2563EB)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = count,
                    fontSize = 12.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}

@Composable
fun PriceFilterChip(range: String, subtitle: String) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocalOffer,
                contentDescription = null,
                tint = Color(0xFF34D399),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = range,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = Color(0xFFA7F3D0)
                )
            }
        }
    }
}

@Composable
fun DealerCard(name: String, rating: String, listings: String) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified",
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = rating,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFBBF24)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = listings,
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RowScreenPreview() {
    RowScreen()
}
