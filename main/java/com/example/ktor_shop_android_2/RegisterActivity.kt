package com.example.ktor_shop_android_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ktor_shop_android_2.models.Customer
import com.example.ktor_shop_android_2.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var button: Button = findViewById(R.id.createAccountButton)
        button.setOnClickListener {
            //retrofitBuilder.createCustomer(Customer("e","e","e","e","e"))
            var id:String = findViewById<EditText>(R.id.idRegField).text.toString()
            var password:String = findViewById<EditText>(R.id.passwordRegField).text.toString()
            var password2:String = findViewById<EditText>(R.id.passwordRegField2).text.toString()
            var name:String = findViewById<EditText>(R.id.nameRegField).text.toString()
            var surname:String = findViewById<EditText>(R.id.surnameRegField).text.toString()
            var email:String = findViewById<EditText>(R.id.emailRegField).text.toString()
            if(password.isEmpty() || password2.isEmpty() || id.isEmpty())
                Toast.makeText(this, "all fields are required", Toast.LENGTH_LONG).show()
            else if(password != password2)
                Toast.makeText(this, "passwords don't match", Toast.LENGTH_LONG).show()
            else{ // check if there is no customer with this id
                val call = retrofitBuilder.idIsUniqueRoute(id)
                call.enqueue(object : Callback<LoginResponse?> {
                    override fun onResponse(call: retrofit2.Call<LoginResponse?>, response: Response<LoginResponse?>) {
                        var responseBody = response.body()!!
                        if(responseBody.response=="good"){
                            // adding customer to database
                            var customer:Customer = Customer(id,name,surname,email,password)
                            val call = retrofitBuilder.createCustomer(customer)
                            call.enqueue(object : Callback<Customer?> {
                                override fun onResponse(call: Call<Customer?>, response: Response<Customer?>) {
                                    Toast.makeText(this@RegisterActivity, "Your account was created", Toast.LENGTH_SHORT).show()
                                }

                                override fun onFailure(call: Call<Customer?>, t: Throwable) {
                                    Toast.makeText(this@RegisterActivity, "Your account was created", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                        else Toast.makeText(this@RegisterActivity, "User with given id already exists", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: retrofit2.Call<LoginResponse?>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_LONG).show()
                    }
                })

            }
        }
    }
}