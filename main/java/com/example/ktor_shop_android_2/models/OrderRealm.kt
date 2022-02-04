package com.example.ktor_shop_android_2.models

import io.realm.RealmObject

open class OrderRealm : RealmObject() {
    var product_id:String=""
    var quantity:Int=0
}