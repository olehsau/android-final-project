package com.example.ktor_shop_android_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ktor_shop_android_2.models.CouponRealm
import io.realm.RealmResults

var priceModifier:Double = 1.0

class RecyclerAdapterCheckout(var context:Context) : RecyclerView.Adapter<RecyclerAdapterCheckout.ViewHolder>() {

    var myInflater: LayoutInflater = LayoutInflater.from(context)
    var couponsList:RealmResults<CouponRealm> = realm.where(CouponRealm::class.java).findAll()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterCheckout.ViewHolder {
        val view: View = myInflater.inflate(R.layout.coupon_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterCheckout.ViewHolder, position: Int) {
        var coupon = couponsList[position]!!
        holder.discountField.text = coupon.discount
        holder.minOrderField.text = coupon.min_order.toString()
        var couponId:String = coupon.id
        holder.useCouponButton.setOnClickListener{
            if(countCartTotal() < coupon.min_order)
                Toast.makeText(checkoutContext, "You can not use this coupon", Toast.LENGTH_LONG).show()
            else{
                var modifier:Double = coupon.discount.substring(0,coupon.discount.length-1).toDouble() // remove % symbol
                modifier = 100-modifier
                modifier /= 100
                oldTotalCheckoutField.text = countCartTotal().toString()
                totalCheckoutField.text = (countCartTotal()*modifier).toString()
                discountView.text="-"+coupon.discount
            }
        }
    }

    override fun getItemCount(): Int {
        return couponsList.size
    }

    class ViewHolder internal constructor(itemView:View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var discountField:TextView = itemView.findViewById(R.id.discountField)
        var minOrderField:TextView = itemView.findViewById(R.id.minOrderField)
        var useCouponButton:Button = itemView.findViewById(R.id.useCouponButton)
        override fun onClick(v: View?) {
            Toast.makeText(checkoutContext, "hello!", Toast.LENGTH_SHORT).show()
        }

    }
}