package com.example.myapplication

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_prueba_comunicacion_webapi.*

class ComunicacionApi {
    val urlApi = "https://patoparra.com/api"



    fun ConseguirUsuarioPorID(appContext:Context, userId:Int) {
        val queue = Volley.newRequestQueue(appContext)

        val url = "$urlApi/cliente/getfromid/$userId"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val gson = Gson()
                val person: Array<Persona> = gson.fromJson(response, Array<Persona>::class.java)
                Toast.makeText(appContext, "Comunicacion Exitosa!", Toast.LENGTH_SHORT).show()
                /*Quitar antes de la entrega el toast*/
            },
            Response.ErrorListener { Toast.makeText(appContext, "No se pudo contactar al servidor. Intentar mas tarde.", Toast.LENGTH_SHORT).show() })

        queue.add(stringRequest)
    }
}