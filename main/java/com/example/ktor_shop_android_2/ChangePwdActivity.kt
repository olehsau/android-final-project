package com.example.ktor_shop_android_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ktor_shop_android_2.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdActivity : AppCompatActivity() {

    lateinit var buttonChangePwd:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pwd)

        buttonChangePwd = findViewById(R.id.buttonChangePwd)
        buttonChangePwd.setOnClickListener{
            val oldPwd:String = findViewById<EditText>(R.id.oldPwd).text.toString()
            val newPwd:String = findViewById<EditText>(R.id.newPwd).text.toString()
            val newPwd2:String = findViewById<EditText>(R.id.newPwd2).text.toString()

            // checking if old password is good and if yes - checking other requirements and
            // finally changing password (this all is inside onResponse)
            if(oldPwd!=null && oldPwd.isNotEmpty()) {
                val call = retrofitBuilder.loginResponseRoute(userId, oldPwd)
                call.enqueue(object : Callback<LoginResponse?> {
                    override fun onResponse(
                        call: Call<LoginResponse?>,
                        response: Response<LoginResponse?>
                    ) {
                        val responseBody = response.body()!!
                        if (responseBody.response == "good") {
                            if (newPwd != newPwd2) {
                                Toast.makeText(
                                    this@ChangePwdActivity,
                                    "New passwords are not the same!",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (newPwd == null || newPwd.isEmpty() || newPwd2 == null || newPwd2.isEmpty()) {
                                Toast.makeText(
                                    this@ChangePwdActivity,
                                    "All fields must be filled",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                val call = retrofitBuilder.changePasswordRoute(userId, newPwd)
                                call.enqueue(object : Callback<Void?> {
                                    override fun onResponse(
                                        call: Call<Void?>,
                                        response: Response<Void?>
                                    ) {

                                    }

                                    override fun onFailure(call: Call<Void?>, t: Throwable) {

                                    }
                                })
                                Toast.makeText(this@ChangePwdActivity, "Success", Toast.LENGTH_LONG)
                                    .show()
                            }
                        } else {
                            Toast.makeText(
                                this@ChangePwdActivity,
                                "Old password is bad",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                        Toast.makeText(this@ChangePwdActivity, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
            else Toast.makeText(this@ChangePwdActivity, "Enter old password", Toast.LENGTH_LONG).show()
        }
    }
}