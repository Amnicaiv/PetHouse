package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_misestancias.*
import kotlinx.android.synthetic.main.layout_misreservaciones.*

class MisEstancias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_misestancias);



        var mLastClickTime = 0.0

        var doublePressPrev = false

        this.btn_registrar_casa.setOnClickListener(){

            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble()
            if(!doublePressPrev) {
                val estancias = Intent(applicationContext, RegistrarEstancia::class.java)
                startActivity(estancias)
            }
        }
/*


        val estanciaItem1= EstanciaItem(nombre = "Asilashon",          direccion  = "Calle falsa123, Col. Benito Tampico,TAMPS", img1 = 1, descripcion = null, tiposMascotas = null, tamaños = null, img2 = null, img3 = null, comprobante = null, servicioComida = null, servicioBaño = null, costoNoche = null)
        val estanciaItem2= EstanciaItem(nombre = "La granja",          direccion  = "Calle falsa123, Col. Benito Tampico,TAMPS", img1 = 1, descripcion = null, tiposMascotas = null, tamaños = null, img2 = null, img3 = null, comprobante = null, servicioComida = null, servicioBaño = null, costoNoche = null)
        val estanciaItem3= EstanciaItem(nombre = "Taquerias Pipe",     direccion  = "Calle falsa123, Col. Benito Tampico,TAMPS", img1 = 1, descripcion = null, tiposMascotas = null, tamaños = null, img2 = null, img3 = null, comprobante = null, servicioComida = null, servicioBaño = null, costoNoche = null)
        val estanciaItem4= EstanciaItem(nombre = "Casa perrito sano",  direccion  = "Calle falsa123, Col. Benito Tampico,TAMPS", img1 = 1, descripcion = null, tiposMascotas = null, tamaños = null, img2 = null, img3 = null, comprobante = null, servicioComida = null, servicioBaño = null, costoNoche = null)
        val estanciaItem5= EstanciaItem(nombre = "Casa de don Segura", direccion  = "Calle falsa123, Col. Benito Tampico,TAMPS", img1 = 1, descripcion = null, tiposMascotas = null, tamaños = null, img2 = null, img3 = null, comprobante = null, servicioComida = null, servicioBaño = null, costoNoche = null)
        val estanciaItem6= EstanciaItem(nombre = "Cielo de canes",     direccion  = "Calle falsa123, Col. Benito Tampico,TAMPS", img1 = 1, descripcion = null, tiposMascotas = null, tamaños = null, img2 = null, img3 = null, comprobante = null, servicioComida = null, servicioBaño = null, costoNoche = null)

        val listaEstancias = mutableListOf(estanciaItem1, estanciaItem2, estanciaItem3,
            estanciaItem4, estanciaItem5, estanciaItem6)

        val listView = this.LV_MisEstancias

        listView.setOnItemClickListener  (){parent,view,position,id->

            val selectedObject = this.LV_MisEstancias.getItemAtPosition(position) as EstanciaItem
            val editarEstan = Intent(applicationContext, EditarEstanciaActivity::class.java)
            startActivity(editarEstan)
        }

        val adapter = MisEstanciasAdapter(this, listaEstancias as ArrayList<EstanciaItem>)
        listView.adapter = adapter




*/

    }

}