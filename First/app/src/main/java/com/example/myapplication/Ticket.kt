package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_misestancias.*
import kotlinx.android.synthetic.main.layout_ticket.*

class Ticket : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ticket);

        this.button17.setOnClickListener(){
            Toast.makeText(this, "Regresar al menu principal", Toast.LENGTH_SHORT).show()
            val menuActivity = Intent(applicationContext, DashboardActivity::class.java)
            startActivity(menuActivity)
        }
    }
}