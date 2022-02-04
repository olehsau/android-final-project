package com.example.ktor_shop_android_2.models

import io.realm.RealmObject

open class ProductRealm : RealmObject() {
    var id:String=""
    var name:String=""
    var price:Double=-1.0
}