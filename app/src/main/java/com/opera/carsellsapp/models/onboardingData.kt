package com.opera.carsellsapp.models

import com.opera.carsellsapp.R

data class OnboardingItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

val onboardingItems = listOf(
    OnboardingItem(
        imageRes = R.drawable.ic_launcher_foreground,
        title = "Find Your Dream Car",
        description = "Browse thousands of verified vehicles from top sellers nationwide."
    ),
    OnboardingItem(
        imageRes = R.drawable.ic_launcher_foreground,
        title = "Sell with Ease",
        description = "List your vehicle in minutes, connect with real buyers, and get top value."
    ),
    OnboardingItem(
        imageRes = R.drawable.ic_launcher_foreground,
        title = "Secure Transactions",
        description = "Fast, transparent, and secure payment and inquiry channels for peace of mind."
    )
)
