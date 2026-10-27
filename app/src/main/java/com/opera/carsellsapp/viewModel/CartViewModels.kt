package com.opera.carsellsapp.viewModel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.opera.carsellsapp.models.CartItem

class CartViewModel(val navController: NavHostController, val context: Context) {

    val cartRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Cart")

    fun addToCart(cartItem: CartItem) {
        val itemId = cartItem.id.ifEmpty { cartRef.push().key ?: System.currentTimeMillis().toString() }
        val newItem = cartItem.copy(id = itemId)
        cartRef.child(itemId).setValue(newItem).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "${cartItem.name} added to inquiry list!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to add to cart: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun fetchCartItems(cartList: MutableList<CartItem>) {
        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartList.clear()
                for (child in snapshot.children) {
                    val item = child.getValue(CartItem::class.java)
                    if (item != null) {
                        cartList.add(item)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load cart: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateQuantity(itemId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(itemId)
            return
        }
        cartRef.child(itemId).child("quantity").setValue(quantity)
    }

    fun removeFromCart(itemId: String) {
        cartRef.child(itemId).removeValue()
    }

    fun clearCart() {
        cartRef.removeValue()
    }
}
