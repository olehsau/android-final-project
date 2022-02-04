package com.example.ktor_shop_android_2

import com.example.ktor_shop_android_2.models.Coupon
import com.example.ktor_shop_android_2.models.Customer
import com.example.ktor_shop_android_2.models.LoginResponse
import com.example.ktor_shop_android_2.models.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @GET("login/{id}/{password}")
    fun loginResponseRoute(@Path("id")id:String, @Path("password")password:String): Call<LoginResponse>

    @GET("customers/{id}/change_password/{new_password}")
    fun changePasswordRoute(@Path("id")id:String, @Path("new_password")newPassword:String): Call<Void>

    @GET("product")
    fun getAllProductsRoute(): Call<ArrayList<Product>>

    @GET("id_is_unique/{id}")
    fun idIsUniqueRoute(@Path("id")id:String): Call<LoginResponse>

    @POST("customers")
    fun createCustomer(@Body customer:Customer):Call<Customer>

    @GET("coupon")
    fun getAllCouponsRoute(): Call<ArrayList<Coupon>>
}