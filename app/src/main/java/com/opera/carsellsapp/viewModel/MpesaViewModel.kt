package com.opera.carsellsapp.viewModel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class MpesaViewModel(val context: Context) {

    fun performSTKPush(
        phone: String,
        amount: Double,
        onLoading: (Boolean) -> Unit,
        onSuccess: () -> Unit
    ) {
        if (phone.isBlank()) {
            Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            return
        }
        onLoading(true)
        Handler(Looper.getMainLooper()).postDelayed({
            onLoading(false)
            Toast.makeText(context, "STK Push sent to $phone for Ksh $amount. Enter M-Pesa PIN to complete transaction.", Toast.LENGTH_LONG).show()
            onSuccess()
        }, 1500)
    }
}
