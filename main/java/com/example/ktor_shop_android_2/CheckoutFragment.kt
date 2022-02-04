package com.example.ktor_shop_android_2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


lateinit var recyclerViewCheckout:RecyclerView
lateinit var proceedButton:Button
lateinit var totalCheckoutField:TextView
lateinit var oldTotalCheckoutField:TextView
lateinit var discountView:TextView
lateinit var checkoutContext:Context

class CheckoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_checkout, container, false)
        checkoutContext = requireContext()
        proceedButton = rootView.findViewById(R.id.proceedButton)
        proceedButton.setOnClickListener { Toast.makeText(context,"Next step is under construction!",Toast.LENGTH_LONG).show()}
        totalCheckoutField = rootView.findViewById(R.id.totalCheckoutField)
        totalCheckoutField.text = countCartTotal().toString()
        oldTotalCheckoutField = rootView.findViewById(R.id.oldTotalField)
        discountView = rootView.findViewById(R.id.discountView)
        // setting up RecyclerView
        recyclerViewCheckout = rootView.findViewById(R.id.recyclerViewCheckout)
        recyclerViewCheckout.layoutManager = LinearLayoutManager(context)
        val adapter = RecyclerAdapterCheckout(checkoutContext) // maybe this context is bad
        recyclerViewCheckout.adapter = adapter

        return rootView
    }

    /*companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckoutFragment().apply {

            }
    }*/
}