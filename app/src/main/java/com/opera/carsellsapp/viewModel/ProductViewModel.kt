package com.opera.carsellsapp.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.opera.carsellsapp.models.Product
import com.opera.carsellsapp.navigations.ROUTE_PRODUCTLIST

class ProductViewModel(val navController: NavHostController, val context: Context) {

    val databasereference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Products")

    fun addProduct(name: String, price: String, description: String, imageUri: Uri?, sellerName: String = "Dealer", sellerContact: String = "0700000000") {
        if (name.isBlank() || price.isBlank() || description.isBlank()) {
            Toast.makeText(context, "Please fill in all vehicle details", Toast.LENGTH_SHORT).show()
            return
        }
        val productId = databasereference.push().key ?: System.currentTimeMillis().toString()
        val imageUrl = imageUri?.toString() ?: ""
        val vehicle = Product(
            id = productId,
            name = name,
            price = price,
            description = description,
            imageUrl = imageUrl,
            sellerName = sellerName,
            sellerContact = sellerContact
        )

        databasereference.child(productId).setValue(vehicle).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Vehicle listed successfully!", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_PRODUCTLIST)
            } else {
                Toast.makeText(context, "Failed to post listing: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun allProducts(productList: MutableList<Product>) {
        databasereference.addValueEventListener(object : ValueEventListener {
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
        val productMap = mutableMapOf<String, Any>(
            "name" to name,
            "price" to price,
            "description" to description
        )
        if (imageUri != null) {
            productMap["imageUrl"] = imageUri.toString()
        }

        databasereference.child(productId).updateChildren(productMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Listing updated successfully!", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_PRODUCTLIST)
            } else {
                Toast.makeText(context, "Update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteProduct(productId: String) {
        databasereference.child(productId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Listing deleted successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to delete: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
