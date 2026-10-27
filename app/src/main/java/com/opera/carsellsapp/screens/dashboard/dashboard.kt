package com.opera.carsellsapp.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.opera.carsellsapp.navigations.ROUTE_ADDPRODUCT
import com.opera.carsellsapp.navigations.ROUTE_BOX
import com.opera.carsellsapp.navigations.ROUTE_CART
import com.opera.carsellsapp.navigations.ROUTE_FIRST
import com.opera.carsellsapp.navigations.ROUTE_MYINTENTS
import com.opera.carsellsapp.navigations.ROUTE_PRODUCTLIST
import com.opera.carsellsapp.navigations.ROUTE_ROW
import com.opera.carsellsapp.navigations.ROUTE_SETTINGS
import com.opera.carsellsapp.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(navController, context) }
    var username by remember { mutableStateOf("Car Enthusiast") }

    LaunchedEffect(Unit) {
        authViewModel.getCurrentUserName { name -> username = name }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("CarSell Dashboard", fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(ROUTE_SETTINGS) }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { authViewModel.signout() }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Sign Out",
                            tint = Color(0xFFEF4444)
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1E293B)) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF2563EB)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_PRODUCTLIST) },
                    icon = { Icon(Icons.Default.DirectionsCar, contentDescription = "Cars") },
                    label = { Text("Listings") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_MYINTENTS) },
                    icon = { Icon(Icons.Default.PhoneInTalk, contentDescription = "Intents") },
                    label = { Text("Intents") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_SETTINGS) },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ROUTE_ADDPRODUCT) },
                containerColor = Color(0xFF2563EB),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Vehicle Listing")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F172A))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // User Welcome Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2563EB)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = username.take(1).uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Hello, $username 👋",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Find, post, and manage vehicle deals",
                            fontSize = 13.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "CarSell Control Center",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Main Actions Grid (2 columns)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardActionCard(
                    title = "Browse Cars",
                    subtitle = "View active listings",
                    icon = Icons.Default.DirectionsCar,
                    accentColor = Color(0xFF2563EB),
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate(ROUTE_PRODUCTLIST)
                }

                DashboardActionCard(
                    title = "Post Car Listing",
                    subtitle = "Sell a vehicle",
                    icon = Icons.Default.Add,
                    accentColor = Color(0xFF10B981),
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate(ROUTE_ADDPRODUCT)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardActionCard(
                    title = "Cart / Inquiries",
                    subtitle = "Vehicle wishlist & pay",
                    icon = Icons.Default.ShoppingCart,
                    accentColor = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate(ROUTE_CART)
                }

                DashboardActionCard(
                    title = "System Intents",
                    subtitle = "Call, SMS, Maps & STK",
                    icon = Icons.Default.PhoneInTalk,
                    accentColor = Color(0xFFEC4899),
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate(ROUTE_MYINTENTS)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Showcase & Demo Features",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF60A5FA)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardActionCard(
                    title = "Spotlight Box",
                    subtitle = "BoxScreen layout",
                    icon = Icons.Default.ViewModule,
                    accentColor = Color(0xFF8B5CF6),
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate(ROUTE_BOX)
                }

                DashboardActionCard(
                    title = "Welcome Screen",
                    subtitle = "FirstScreen overview",
                    icon = Icons.Default.Web,
                    accentColor = Color(0xFF06B6D4),
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate(ROUTE_FIRST)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            DashboardActionCard(
                title = "Categories & Pricing Row Showcase",
                subtitle = "RowScreen vehicle type & price filters showcase",
                icon = Icons.Default.ViewCarousel,
                accentColor = Color(0xFF14B8A6),
                modifier = Modifier.fillMaxWidth()
            ) {
                navController.navigate(ROUTE_ROW)
            }
        }
    }
}

@Composable
fun DashboardActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}
