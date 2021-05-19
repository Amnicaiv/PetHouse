package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_pet.*

class MisMascotasActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_pet)
        actionBar?.hide()
        supportActionBar?.hide()

        val mascotaItem1= MascotaItem(nombre = "Wilson", raza = "Perro", foto =  1)
        val mascotaItem2= MascotaItem(nombre = "Copito", raza = "Gato", foto =  1)
        val mascotaItem3= MascotaItem(nombre = "Toby", raza = "Perro", foto =  1)
        val mascotaItem4= MascotaItem(nombre = "Zeus", raza = "Perro", foto =  1)
        val mascotaItem5= MascotaItem(nombre = "Soso", raza = "Perrico", foto =  1)
        val mascotaItem6= MascotaItem(nombre = "Talegas", raza = "Hamster", foto =  1)

        val listaMascotas = mutableListOf(mascotaItem1, mascotaItem2, mascotaItem3,mascotaItem4, mascotaItem5, mascotaItem6)

        val listView = this.MascotaLV

        listView.setOnItemClickListener(){parent,view,position,id->

            val selectedObject = this.MascotaLV.getItemAtPosition(position) as MascotaItem
            Toast.makeText(this, selectedObject.nombre, Toast.LENGTH_LONG).show()

            val mascotaInfo = Intent(applicationContext, PetInfiActivity::class.java)
            mascotaInfo.putExtra("nombre",selectedObject.nombre.toString())
            mascotaInfo.putExtra("raza",selectedObject.raza.toString())

            startActivity(mascotaInfo)
        }

        val adapter = MascotaAdapter(this, listaMascotas as ArrayList<MascotaItem>)
        listView.adapter = adapter
    }
}