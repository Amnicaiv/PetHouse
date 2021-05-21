package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_3.*

class ShowLayout3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("Activity",this.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3);
        Log.e("IntentActivityInfo",intent.getStringExtra("data").toString());
        val strDatos:String = intent.getStringExtra("data").toString();

        this.lblCorreo.setText(strDatos);
    }
}