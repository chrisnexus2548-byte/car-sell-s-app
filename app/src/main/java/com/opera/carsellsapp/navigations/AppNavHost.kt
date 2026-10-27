package com.opera.carsellsapp.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.opera.carsellsapp.screens.cart.CartScreen
import com.opera.carsellsapp.screens.dashboard.DashboardScreen
import com.opera.carsellsapp.screens.demo.BoxScreen
import com.opera.carsellsapp.screens.demo.FirstScreen
import com.opera.carsellsapp.screens.demo.IntentScreen
import com.opera.carsellsapp.screens.demo.RowScreen
import com.opera.carsellsapp.screens.login.LoginScreen
import com.opera.carsellsapp.screens.onboardingscreen.OnboardingScreen
import com.opera.carsellsapp.screens.products.AddProductScreen
import com.opera.carsellsapp.screens.products.ProductListScreen
import com.opera.carsellsapp.screens.products.UpdateProductScreen
import com.opera.carsellsapp.screens.profile.ProfileScreen
import com.opera.carsellsapp.screens.register.RegisterScreen
import com.opera.carsellsapp.screens.settings.SettingsScreen
import com.opera.carsellsapp.screens.splashscreen.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(ROUTE_ONBOARDING) {
            OnboardingScreen(navController = navController)
        }
        composable(ROUTE_LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController = navController)
        }
        composable(ROUTE_DASHBOARD) {
            DashboardScreen(navController = navController)
        }
        composable(ROUTE_PRODUCTLIST) {
            ProductListScreen(navController = navController)
        }
        composable(ROUTE_ADDPRODUCT) {
            AddProductScreen(navController = navController)
        }
        composable(
            "$ROUTE_UPDATEPRODUCT/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id") ?: ""
            UpdateProductScreen(navController = navController, productId = productId)
        }
        composable(ROUTE_CART) {
            CartScreen(navController = navController)
        }
        composable(ROUTE_PROFILE) {
            ProfileScreen(navController = navController)
        }
        composable(ROUTE_SETTINGS) {
            SettingsScreen(navController = navController)
        }
        composable(ROUTE_MYINTENTS) {
            IntentScreen(navController = navController)
        }
        composable(ROUTE_BOX) {
            BoxScreen(navController = navController)
        }
        composable(ROUTE_FIRST) {
            FirstScreen(navController = navController)
        }
        composable(ROUTE_ROW) {
            RowScreen(navController = navController)
        }
    }
}
