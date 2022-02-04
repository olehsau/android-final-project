package com.example.ktor_shop_android_2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ktor_shop_android_2.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var allProductsContext:Context

class AllProductsActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    lateinit var buttonCart: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_products)
        allProductsContext = this@AllProductsActivity

        // setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter(this)
        recyclerView.setAdapter(adapter)

        // button Cart listener
        buttonCart = findViewById(R.id.buttonMyCart)
        buttonCart.setOnClickListener{
            val intentCart: Intent = Intent(this@AllProductsActivity, CartActivity::class.java).apply{}
            startActivity(intentCart)
        }

    }

}