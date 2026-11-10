package com.opera.carsellsapp.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.opera.carsellsapp.models.Product
import com.opera.carsellsapp.navigations.ROUTE_PRODUCTLIST
import com.opera.carsellsapp.network.CloudinaryAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel(val navController: NavHostController, val context: Context) {

    val databasereference: DatabaseReference? by lazy {
        try {
            if (FirebaseApp.getApps(context).isNotEmpty()) {
                FirebaseDatabase.getInstance().getReference("Products")
            } else null
        } catch (_: Exception) {
            null
        }
    }
    val cloudinaryUrl = "https://api.cloudinary.com/v1_1/dojp0mlml/upload"
    val uploadPreset = "caparck"

    private suspend fun uploadToCloudinary(context: Context, uri: Uri): String {
        // Get the selected image
        val inputStream = context.contentResolver.openInputStream(uri) ?: throw Exception("Image read failed")
        // Convert image to bytes
        val fileBytes = inputStream.readBytes()
        // Create image request body
        val requestBody = fileBytes.toRequestBody("image/*".toMediaType())
        // Create multipart file
        val filePart = MultipartBody.Part.createFormData("file", "image.jpg", requestBody)
        // Create upload preset
        val preset = uploadPreset.toRequestBody("text/plain".toMediaType())
        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.cloudinary.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Create Cloudinary API
        val cloudinaryAPI = retrofit.create(
            CloudinaryAPI::class.java
        )
        // Make the Cloudinary API call
        val response = cloudinaryAPI.uploadImage(filePart, preset)
        // Check response
        if (response.isSuccessful) {
            // Get Cloudinary image URL
            return response.body()?.secure_url
                ?: throw Exception("Image URL not found")
        } else {
            throw Exception(
                "Cloudinary upload failed: ${response.message()}"
            )
        }
    }

    fun addProduct(name: String, price: String, description: String, imageUri: Uri?, sellerName: String = "Dealer", sellerContact: String = "0700000000") {
        if (name.isBlank() || price.isBlank() || description.isBlank()) {
            Toast.makeText(context, "Please fill in all vehicle details", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(context, "Uploading image...", Toast.LENGTH_SHORT).show()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imageUrl = if (imageUri != null) {
                    uploadToCloudinary(context, imageUri)
                } else {
                    ""
                }

                val productId = databasereference?.push()?.key ?: System.currentTimeMillis().toString()
                val vehicle = Product(
                    id = productId,
                    name = name,
                    price = price,
                    description = description,
                    imageUrl = imageUrl,
                    sellerName = sellerName,
                    sellerContact = sellerContact
                )

                withContext(Dispatchers.Main) {
                    val dbRef = databasereference
                    if (dbRef == null) {
                        Toast.makeText(context, "Database connection not available", Toast.LENGTH_SHORT).show()
                        return@withContext
                    }
                    dbRef.child(productId).setValue(vehicle).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Vehicle listed successfully!", Toast.LENGTH_SHORT).show()
                            navController.navigate(ROUTE_PRODUCTLIST)
                        } else {
                            Toast.makeText(context, "Failed to post listing: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun allProducts(productList: MutableList<Product>) {
        databasereference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (child in snapshot.children) {
                    val product = child.getValue(Product::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error fetching listings: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateProduct(productId: String, name: String, price: String, description: String, imageUri: Uri?) {
        Toast.makeText(context, "Updating listing...", Toast.LENGTH_SHORT).show()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productMap = mutableMapOf<String, Any>(
                    "name" to name,
                    "price" to price,
                    "description" to description
                )
                if (imageUri != null) {
                    val imageUrl = uploadToCloudinary(context, imageUri)
                    productMap["imageUrl"] = imageUrl
                }

                withContext(Dispatchers.Main) {
                    val dbRef = databasereference
                    if (dbRef == null) {
                        Toast.makeText(context, "Database connection not available", Toast.LENGTH_SHORT).show()
                        return@withContext
                    }
                    dbRef.child(productId).updateChildren(productMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Listing updated successfully!", Toast.LENGTH_SHORT).show()
                            navController.navigate(ROUTE_PRODUCTLIST)
                        } else {
                            Toast.makeText(context, "Update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Update failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun deleteProduct(productId: String) {
        databasereference?.child(productId)?.removeValue()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Listing deleted successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to delete: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
