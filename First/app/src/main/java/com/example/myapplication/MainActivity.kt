package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_camera_layout.*

import kotlinx.android.synthetic.main.layout_dashboard.*

import kotlinx.android.synthetic.main.layout_iniciosesion.*
import kotlinx.android.synthetic.main.layout_login.*
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_iniciosesion);

        actionBar?.hide()
        supportActionBar?.hide()

        var mLastClickTime = 0.0

        var doublePressPrev = false

        val apiComm = ComunicacionApi()

        this.button3.setOnClickListener(){

           /* apiComm.ConseguirUsuarioPorID(this,1)*/
            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble();

            if(!doublePressPrev){
                val menuActivity = Intent(applicationContext, DashboardActivity::class.java)
                startActivity(menuActivity)
            }
        }

        this.button15.setOnClickListener(){
            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble();

            if(!doublePressPrev) {
                val registerActivity = Intent(applicationContext, SignUpActivity::class.java)
                startActivity(registerActivity)
            }
        }



    }


}
