package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_petinfo.*

class PetInfiActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_petinfo)

        val petName = intent.getStringExtra("nombre")
        val petType = intent.getStringExtra("raza")

        this.editTextTextPersonName6.setText(petName)

        var mLastClickTime = 0.0

        var doublePressPrev = false

        this.button9.setOnClickListener(){
            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble();

            if(!doublePressPrev){
                finish()
            }
        }



        this.button11.setOnClickListener(){
            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble()

            if(!doublePressPrev){
                finish()
            }
        }


    }

}