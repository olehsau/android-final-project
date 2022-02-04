package com.example.ktor_shop_android_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ktor_shop_android_2.models.OrderRealm
import com.example.ktor_shop_android_2.models.ProductRealm
import io.realm.kotlin.where
import java.math.RoundingMode

lateinit var cartTotalView:TextView

class CartActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        cartTotalView = findViewById(R.id.cartTotal)

        // setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapterCart(this)
        recyclerView.setAdapter(adapter)

        countCartTotal()

        // checkout button listener
        var checkoutButton: Button = findViewById(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frameLayoutCheckout, CheckoutFragment()).commit()
        }
    }
}

fun countCartTotal(): Double{
    var cartTotal:Double = 0.0
    var ordersList = realm.where(OrderRealm::class.java).findAll()
    for(order in ordersList){
        var price:Double = realm.where(ProductRealm::class.java).findAll().where().equalTo("id",order.product_id).findFirst()!!.price
        cartTotal += (price*order.quantity)
    }
    cartTotal = cartTotal.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
    cartTotalView.text = cartTotal.toString()
    return cartTotal
}