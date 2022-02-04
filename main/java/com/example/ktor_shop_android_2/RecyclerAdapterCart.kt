package com.example.ktor_shop_android_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ktor_shop_android_2.models.OrderRealm
import com.example.ktor_shop_android_2.models.ProductRealm
import io.realm.RealmResults

class RecyclerAdapterCart(var context: Context) : RecyclerView.Adapter<RecyclerAdapterCart.ViewHolder>() {

    var myInflater: LayoutInflater = LayoutInflater.from(context)
    var ordersList: RealmResults<OrderRealm> = realm.where(OrderRealm::class.java).findAll()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterCart.ViewHolder {
        val view: View = myInflater.inflate(R.layout.order_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterCart.ViewHolder, position: Int) {
        var order = ordersList[position]!!
        var price:Double = realm.where(ProductRealm::class.java).findAll().where().equalTo("id",order.product_id).findFirst()!!.price
        holder.orderNameView.text =  realm.where(ProductRealm::class.java).findAll().where().equalTo("id",order.product_id).findFirst()!!.name
        holder.orderQuantityView.text = order.quantity.toString()
        holder.orderPriceView.text = price.toString()
        holder.orderTotalView.text = (price*order.quantity).toString()
        holder.orderDeleteButton.setOnClickListener{
            realm.beginTransaction()
            order.deleteFromRealm()
            realm.commitTransaction()
            notifyItemRemoved(position)
            countCartTotal()
        }
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var orderNameView:TextView = itemView.findViewById(R.id.orderName)
        var orderPriceView:TextView = itemView.findViewById(R.id.orderPrice)
        var orderQuantityView:TextView = itemView.findViewById(R.id.orderQuantity)
        var orderTotalView:TextView = itemView.findViewById(R.id.orderTotal)
        var orderDeleteButton: ImageView = itemView.findViewById(R.id.orderDeleteButton)
        override fun onClick(v: View?) {

        }
    }

}