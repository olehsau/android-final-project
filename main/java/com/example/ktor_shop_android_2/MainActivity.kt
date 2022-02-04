package com.example.ktor_shop_android_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ktor_shop_android_2.models.Customer
import com.example.ktor_shop_android_2.models.LoginResponse
import com.example.ktor_shop_android_2.models.Product
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.StringBuilder

const val BASE_URL = "http://310b-31-172-187-154.ngrok.io/"

val retrofitBuilder = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
    .create(ApiInterface::class.java)

class MainActivity : AppCompatActivity() {

    lateinit var enteredId:EditText
    lateinit var enteredPassword:EditText
    lateinit var buttonLogin:Button
    lateinit var buttonRegister:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // login button listener
        buttonLogin = findViewById<Button>(R.id.loginButton)
        enteredId = findViewById<EditText>(R.id.loginIdField)
        enteredPassword = findViewById<EditText>(R.id.loginPasswordField)
        buttonLogin.setOnClickListener {
            if (validateLogin(enteredId.text.toString(), enteredPassword.text.toString())) {
                doLogin(enteredId.text.toString(), enteredPassword.text.toString())
            }
        }

        //register button listener
        buttonRegister = findViewById(R.id.registerButton)
        buttonRegister.setOnClickListener {
            var intentRegister:Intent = Intent(this, RegisterActivity::class.java).apply{}
            startActivity(intentRegister)
        }
    }

    fun validateLogin(id:String, password:String): Boolean{
        //Toast.makeText(this,id+password,Toast.LENGTH_SHORT).show()
        if(id==null || id.isEmpty()){
            Toast.makeText(this,"Id is required!!!",Toast.LENGTH_SHORT).show()
            return false
        }
        if(password==null || password.isEmpty()){
            Toast.makeText(this,"Password is required!!!",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun doLogin(id:String, password:String){
        val call:Call<LoginResponse> = retrofitBuilder.loginResponseRoute(id, password)
        call.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                if(response.isSuccessful()){
                    val responseBody = response.body()!!
                    if(responseBody.response=="good"){
                        val intentLogin = Intent(this@MainActivity, MenuActivity::class.java).apply{
                            putExtra("id",id)
                        }
                        startActivity(intentLogin)
                        finish()
                    }
                    else{
                        Toast.makeText(this@MainActivity, "The id or password is incorrect!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@MainActivity, "Error! Please try again!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}