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

        val descripcion = intent.getStringExtra("RES_DESC")
        val propietario = intent.getStringExtra("RES_PROP")
        val fechaEntrada = intent.getStringExtra("RES_FECHAE")
        val fechaSalida = intent.getStringExtra("RES_FECHAS")
        val totalPagado = intent.getDoubleExtra("RES_TOTAL", 0.0)

        this.ET_DescpHogar_Ticket.setText(descripcion)
        this.ET_Propietaria_Ticket.setText(propietario)
        this.ET_FechaEntrada_Ticket.setText(fechaEntrada.toString().substring(0,10))
        this.ET_FechaSalida_Ticket.setText(fechaSalida.toString().substring(0,10))
        this.ET_Total_Ticket.setText(totalPagado.toString())

        this.ET_DescpHogar_Ticket.isEnabled = false
        this.ET_Propietaria_Ticket.isEnabled = false
        this.ET_FechaEntrada_Ticket.isEnabled = false
        this.ET_FechaSalida_Ticket.isEnabled = false
        this.ET_Total_Ticket.isEnabled = false

        this.btn_ticket_regresar.setOnClickListener{
            finish()
        }
    }
}