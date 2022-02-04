package com.example.ktor_shop_android_2.models

import io.realm.RealmObject

open class CouponRealm : RealmObject(){
    var id:String=""
    var discount:String=""
    var min_order:Double=-1.0
}