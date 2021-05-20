package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_misreservaciones.*
import kotlinx.android.synthetic.main.layout_pet.*

class MisReservacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_misreservaciones);


        val reservacionItem1= ReservacionItem(nombre = "Casa perrito sano",  fechaEntrada  = "3/17", fechaSalida ="3/20", foto =  1)
        val reservacionItem2= ReservacionItem(nombre = "Asilashon",          fechaEntrada  = "5/12", fechaSalida ="5/20", foto =  1)
        val reservacionItem3= ReservacionItem(nombre = "La granja",          fechaEntrada  = "9/7",  fechaSalida ="9/10", foto =  1)
        val reservacionItem4= ReservacionItem(nombre = "Taquerias Pipe",     fechaEntrada  = "5/6",  fechaSalida ="5/7", foto =  1)
        val reservacionItem5= ReservacionItem(nombre = "Casa de don Segura", fechaEntrada  = "6/7",  fechaSalida ="6/8", foto =  1)
        val reservacionItem6= ReservacionItem(nombre = "Cielo de canes",     fechaEntrada  = "1/15", fechaSalida ="1/17", foto =  1)

        val listaReservaciones = mutableListOf(reservacionItem1, reservacionItem2, reservacionItem3,
            reservacionItem4, reservacionItem5, reservacionItem6)

        val listView = this.LV_MisReservaciones

        listView.setOnItemClickListener(){parent,view,position,id->

            val selectedObject = this.LV_MisReservaciones.getItemAtPosition(position) as ReservacionItem
            val ticket = Intent(applicationContext, Ticket::class.java)
            startActivity(ticket)
        }

        val adapter = MisReservacionesAdapter(this, listaReservaciones as ArrayList<MascotaItem>)
        listView.adapter = adapter

        this.button7.setOnClickListener(){
            finish()
        }

    }
}