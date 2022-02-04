package com.example.ktor_shop_android_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ktor_shop_android_2.models.OrderRealm

import com.example.ktor_shop_android_2.models.Product
import com.example.ktor_shop_android_2.models.ProductRealm
import io.realm.RealmResults


//lateinit var myClickListener:RecyclerAdapter.ItemClickListener

class RecyclerAdapter(var context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var myInflater:LayoutInflater = LayoutInflater.from(context)
    var productsList:RealmResults<ProductRealm> = realm.where(ProductRealm::class.java).findAll()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = myInflater.inflate(R.layout.product_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        //val product: Product = myData[position]
        var product = productsList[position]!!
        holder.TextViewName.text = product.name
        holder.TextViewPrice.text = product.price.toString()
        holder.ButtonAddToCart.setOnClickListener{
            var quantity:Int = holder.ProductQuantity.text.toString().toInt()
            var order:OrderRealm = OrderRealm()
            order.product_id = product.id
            order.quantity = quantity
            realm.beginTransaction()
            val oldOrders:RealmResults<OrderRealm> = realm.where(OrderRealm::class.java).findAll()
            if(oldOrders.where().equalTo("product_id",product.id).findFirst() != null) {
                var oldOrder: OrderRealm = oldOrders.where().equalTo("product_id", product.id).findFirst()!!
                oldOrder.deleteFromRealm()
            }
            realm.insert(order)
            realm.commitTransaction()
            Toast.makeText(allProductsContext, "x${quantity} ${product.name} added to cart (no)", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var TextViewName: TextView = itemView.findViewById(R.id.productName)
        var TextViewPrice: TextView = itemView.findViewById(R.id.productPrice)
        var ButtonAddToCart: Button = itemView.findViewById(R.id.productAddToCart)
        var ProductQuantity: EditText = itemView.findViewById(R.id.productQuantity)
        override fun onClick(v: View?) {

        }
    }

    fun getItem(id: Int): ProductRealm? {
        return productsList[id]
    }

}