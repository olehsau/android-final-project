package com.example.ktor_shop_android_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.ktor_shop_android_2.models.Coupon
import com.example.ktor_shop_android_2.models.CouponRealm
import com.example.ktor_shop_android_2.models.Product
import com.example.ktor_shop_android_2.models.ProductRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.kotlin.createObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var userId:String = ""
lateinit var realm:Realm

class MenuActivity : AppCompatActivity() {

    lateinit var greetingView:TextView
    lateinit var exitButton:Button
    lateinit var changePasswordButton:Button
    lateinit var showDrawerButton:AppCompatImageButton
    var drawerIsShown:Boolean = false
    lateinit var allProductsButton:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        greetingView = findViewById(R.id.greeting)
        var extras:Bundle = intent.extras!!
        userId = extras.getString("id")!!
        greetingView.text = "Hello, $userId."

        // drawer button listener
        showDrawerButton = findViewById(R.id.showDrawerButton)
        showDrawerButton.setOnClickListener{
            if(drawerIsShown==false) supportFragmentManager!!.beginTransaction().replace(R.id.frameLayout,DrawerFragment()).commit()
            else supportFragmentManager!!.beginTransaction().replace(R.id.frameLayout,EmptyFragment()).commit()
            drawerIsShown = !drawerIsShown
        }

        // all products button listener
        allProductsButton = findViewById(R.id.allProductsButton)
        allProductsButton.setOnClickListener{
            val intentAllProducts = Intent(this@MenuActivity, AllProductsActivity::class.java).apply{}
            startActivity(intentAllProducts)
        }

        // realm initialization
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        realm = Realm.getInstance(realmConfiguration)

        // get all products to realm right after login
        val call = retrofitBuilder.getAllProductsRoute()
        call.enqueue(object : Callback<ArrayList<Product>?> {
            override fun onResponse(call: Call<ArrayList<Product>?>, response: Response<ArrayList<Product>?>) {
                var productsList:ArrayList<Product> = response.body()!!
                realm.beginTransaction()
                realm.delete(ProductRealm::class.java)
                var product: ProductRealm
                for(p in productsList){
                    product = realm.createObject(ProductRealm::class.java)
                    product.id = p.id
                    product.name = p.name
                    product.price = p.price
                    realm.insert(product)
                }
                realm.commitTransaction()
            }
            override fun onFailure(call: Call<ArrayList<Product>?>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Loading products error", Toast.LENGTH_LONG).show()
            }
        })

        // get all coupons to realm right after login
        val callCoupons = retrofitBuilder.getAllCouponsRoute()
        callCoupons.enqueue(object : Callback<ArrayList<Coupon>?> {
            override fun onResponse(call: Call<ArrayList<Coupon>?>, response: Response<ArrayList<Coupon>?>) {
                var couponsList:ArrayList<Coupon> = response.body()!!
                realm.beginTransaction()
                realm.delete(CouponRealm::class.java)
                var coupon:CouponRealm
                for(c in couponsList){
                    coupon = realm.createObject(CouponRealm::class.java)
                    coupon.id = c.id
                    coupon.discount = c.discount
                    coupon.min_order = c.min_order
                    realm.insert(coupon)
                }
                realm.commitTransaction()
            }

            override fun onFailure(call: Call<ArrayList<Coupon>?>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Loading coupons error", Toast.LENGTH_LONG).show()
            }
        })
    }

}