package com.example.ktor_shop_android_2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.app.Activity




class DrawerFragment : Fragment() {

    private lateinit var logoutButton: Button
    private lateinit var changePwdButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_drawer, container, false)
        logoutButton = rootView.findViewById(R.id.logoutButton)
        changePwdButton = rootView.findViewById(R.id.changePwdButton)

        // exit button listener
        logoutButton.setOnClickListener{
            userId = ""
            val intentExit = Intent(context,MainActivity::class.java).apply{}
            startActivity(intentExit)
            (context as MenuActivity).finish()
        }

        // change password button listener
        changePwdButton.setOnClickListener{
            val intentChangePwd = Intent(context,ChangePwdActivity::class.java).apply{}
            startActivity(intentChangePwd)
        }

        return rootView
    }
}