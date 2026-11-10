package com.opera.carsellsapp.viewModel

import android.content.Context
import android.os.Build
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.opera.carsellsapp.models.User
import com.opera.carsellsapp.navigations.ROUTE_DASHBOARD
import com.opera.carsellsapp.navigations.ROUTE_LOGIN

class AuthViewModel(val navController: NavHostController, val context: Context) {

    // Lazy initialization of Firebase instances with safe checks so Compose Previews don't throw IllegalStateException when FirebaseApp is not initialized
    val mAuth: FirebaseAuth? by lazy {
        try {
            if (FirebaseApp.getApps(context).isNotEmpty()) {
                FirebaseAuth.getInstance()
            } else null
        } catch (_: Exception) {
            null
        }
    }

    val database: FirebaseDatabase? by lazy {
        try {
            if (FirebaseApp.getApps(context).isNotEmpty()) {
                FirebaseDatabase.getInstance()
            } else null
        } catch (_: Exception) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun signup(fullname: String, email: String, pass: String, confpass: String, role: String = "Buyer") {
        val trimmedEmail = email.trim()
        val trimmedFullname = fullname.trim()

        if (trimmedFullname.isBlank() || trimmedEmail.isBlank() || pass.isBlank() || confpass.isBlank()) {
            Toast.makeText(context, "Please fill in all details", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass != confpass) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }


        // Ensure clean session state when creating a new account
        mAuth?.signOut()

        val auth = mAuth
        if (auth == null) {
            Toast.makeText(context, "Firebase authentication is not available", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(trimmedEmail, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: ""
                val userData = User(id = userId, fullname = trimmedFullname, email = trimmedEmail, role = role)
                database?.getReference("Users")?.child(userId)?.setValue(userData)?.addOnCompleteListener { userTask ->
                    if (userTask.isSuccessful) {
                        Toast.makeText(context, "Account created successfully for $trimmedFullname!", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUTE_DASHBOARD) {
                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Failed to save user data: ${userTask.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val errorMessage = task.exception?.message ?: "Registration failed"
                if (errorMessage.contains("already in use", ignoreCase = true)) {
                    Toast.makeText(context, "This email is already registered! Redirecting to Login...", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_LOGIN)
                } else {
                    Toast.makeText(context, "Registration failed: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun login(email: String, pass: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank() || pass.isBlank()) {
            Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }
        val auth = mAuth
        if (auth == null) {
            Toast.makeText(context, "Firebase authentication is not available", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(trimmedEmail, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_DASHBOARD) {
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                }
            } else {
                Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signout() {
        mAuth?.signOut()
        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(ROUTE_DASHBOARD) { inclusive = true }
        }
    }

    fun getCurrentUserName(onResult: (String) -> Unit) {
        val uid = mAuth?.currentUser?.uid
        if (uid == null) {
            onResult("Guest")
            return
        }
        database?.getReference("Users")?.child(uid)?.get()?.addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(User::class.java)
            onResult(user?.fullname ?: "User")
        }?.addOnFailureListener {
            onResult("Car Enthusiast")
        } ?: onResult("Car Enthusiast")
    }

    fun getUserProfile(onResult: (User?) -> Unit) {
        val uid = mAuth?.currentUser?.uid
        if (uid == null) {
            onResult(null)
            return
        }
        database?.getReference("Users")?.child(uid)?.get()?.addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(User::class.java)
            onResult(user)
        }?.addOnFailureListener {
            onResult(null)
        } ?: onResult(null)
    }
}
