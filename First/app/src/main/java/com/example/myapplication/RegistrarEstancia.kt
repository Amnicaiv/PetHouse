package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.HouseModel
import kotlinx.android.synthetic.main.layout_registroresidencia.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class RegistrarEstancia :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registroresidencia)


        this.btn_enviar_solicitud.setOnClickListener(){

            val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
            val idString = prefs.getString("id","Unkown")

            val descripcion = this.editTextTextPersonName3.text.toString()
            val costoxNoche = this.editTextNumber.text.toString().toDouble()
            val capacidad = this.editTextTextPersonName7.text.toString().toInt()
            val cuentaMascotas = this.checkBox3.isChecked

            val casaNueva = HouseModel(idString,descripcion,costoxNoche,capacidad,cuentaMascotas,true,true,true,"2021-06-04T15:26:27.083Z")

            var client = OkHttpClient()
            var request = OkHttpRequest(client)

            var url="https://patoparra.com/api/Hogar/Create"

            request.setHouse(url,casaNueva,object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Error al subir tu informacion a la nube, intentar de nuevo mas tarde.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body()?.string()
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Exito al registrar su residencia.", Toast.LENGTH_LONG).show()
                    }
                    Log.d("reqSuccess",responseData.toString())
                    finish()

                }

            })

        }

    }
}