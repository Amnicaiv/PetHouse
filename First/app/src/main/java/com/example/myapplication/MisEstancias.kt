package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_iniciosesion.*
import kotlinx.android.synthetic.main.layout_misestancias.*

class MisEstancias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_misestancias);

        var mLastClickTime = 0.0

        var doublePressPrev = false

        this.button9.setOnClickListener(){

            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble()
            if(!doublePressPrev) {
                val menuActivity = Intent(applicationContext, DashboardActivity::class.java)
                startActivity(menuActivity)
            }
        }
    }

}