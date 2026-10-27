package com.opera.carsellsapp.viewModel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.opera.carsellsapp.models.User
import com.opera.carsellsapp.navigations.ROUTE_DASHBOARD
import com.opera.carsellsapp.navigations.ROUTE_LOGIN

class AuthViewModel(val navController: NavHostController, val context: Context) {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun signup(fullname: String, email: String, pass: String, confpass: String, role: String = "Buyer") {
        if (fullname.isBlank() || email.isBlank() || pass.isBlank() || confpass.isBlank()) {
            Toast.makeText(context, "Please fill in all details", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass != confpass) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = mAuth.currentUser?.uid ?: ""
                val userData = User(id = userId, fullname = fullname, email = email, role = role)
                database.getReference("Users").child(userId).setValue(userData).addOnCompleteListener { userTask ->
                    if (userTask.isSuccessful) {
                        Toast.makeText(context, "Registered successfully!", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUTE_DASHBOARD) {
                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Failed to save user info: ${userTask.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_DASHBOARD) {
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                }
            } else {
                Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signout() {
        mAuth.signOut()
        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(ROUTE_DASHBOARD) { inclusive = true }
        }
    }

    fun getCurrentUserName(onResult: (String) -> Unit) {
        val uid = mAuth.currentUser?.uid
        if (uid == null) {
            onResult("Guest")
            return
        }
        database.getReference("Users").child(uid).get().addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(User::class.java)
            onResult(user?.fullname ?: "User")
        }.addOnFailureListener {
            onResult("Car Enthusiast")
        }
    }

    fun getUserProfile(onResult: (User?) -> Unit) {
        val uid = mAuth.currentUser?.uid
        if (uid == null) {
            onResult(null)
            return
        }
        database.getReference("Users").child(uid).get().addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(User::class.java)
            onResult(user)
        }.addOnFailureListener {
            onResult(null)
        }
    }
}
