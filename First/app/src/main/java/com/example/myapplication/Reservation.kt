package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_reservacion_checkout.*
import kotlinx.android.synthetic.main.layout_ticket.*

class Reservation : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reservacion_checkout);

        this.button16.setOnClickListener(){
            Toast.makeText(this, "Mandar confirmacion a ticket", Toast.LENGTH_SHORT).show()
            val ticketActivity = Intent(applicationContext, Ticket::class.java)
            startActivity(ticketActivity)
        }


    }



}