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

        var comunicationSuccess = 0
        var success = 0

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val gson = Gson()
                val persona: Array<Persona> = gson.fromJson(response, Array<Persona>::class.java)
                /*Toast.makeText(appContext, "Comunicacion Exitosa!", Toast.LENGTH_SHORT).show()*/
                success = 1
                comunicationSuccess =1
                /*Quitar antes de la entrega el toast*/
            },
            Response.ErrorListener {
                Toast.makeText(appContext, "No se pudo contactar al servidor. Intentar mas tarde.", Toast.LENGTH_SHORT).show()
                success = 0
                comunicationSuccess=1
            })

        queue.add(stringRequest)

        if (comunicationSuccess == 1){
            Toast.makeText(appContext, "Awaiting ", Toast.LENGTH_SHORT).show()
        }

    }


    fun ConseguirHogarPorId(appContext:Context, userId:Int) {
        val queue = Volley.newRequestQueue(appContext)


        val url = "$urlApi/Hogar/GetFromCliente/$userId"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val gson = Gson()
                val estanciaItem: Array<EstanciaItem> = gson.fromJson(response, Array<EstanciaItem>::class.java)
                Toast.makeText(appContext, "Comunicacion Exitosa!", Toast.LENGTH_SHORT).show()
                /*Quitar antes de la entrega el toast*/
            },
            Response.ErrorListener { Toast.makeText(appContext, "No se pudo contactar al servidor. Intentar mas tarde.", Toast.LENGTH_SHORT).show() })

        queue.add(stringRequest)
    }

    fun ConseguirMascota(appContext:Context, userId:Int) {
        val queue = Volley.newRequestQueue(appContext)


        val url = "$urlApi/Mascota/GetAll"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val gson = Gson()
                val mascotaItem: Array<MascotaItem> = gson.fromJson(response, Array<MascotaItem>::class.java)
                Toast.makeText(appContext, "Comunicacion Exitosa!", Toast.LENGTH_SHORT).show()
                /*Quitar antes de la entrega el toast*/
            },
            Response.ErrorListener { Toast.makeText(appContext, "No se pudo contactar al servidor. Intentar mas tarde.", Toast.LENGTH_SHORT).show() })

        queue.add(stringRequest)
    }

    fun ConseguirCiudadPorId(appContext:Context, userId:Int) {
        val queue = Volley.newRequestQueue(appContext)


        val url = "$urlApi/Ciudad/Get/$userId"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val gson = Gson()
                val ciudadItem: Array<CiudadItem> = gson.fromJson(response, Array<CiudadItem>::class.java)
                Toast.makeText(appContext, "Comunicacion Exitosa!", Toast.LENGTH_SHORT).show()
                /*Quitar antes de la entrega el toast*/
            },
            Response.ErrorListener { Toast.makeText(appContext, "No se pudo contactar al servidor. Intentar mas tarde.", Toast.LENGTH_SHORT).show() })

        queue.add(stringRequest)
    }

    fun ConseguirEstadoPorId(appContext:Context, userId:Int) {
        val queue = Volley.newRequestQueue(appContext)


        val url = "$urlApi/Estado/Get/$userId"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val gson = Gson()
                val estadoItem: Array<EstadoItem> = gson.fromJson(response, Array<EstadoItem>::class.java)
                Toast.makeText(appContext, "Comunicacion Exitosa!", Toast.LENGTH_SHORT).show()
                /*Quitar antes de la entrega el toast*/
            },
            Response.ErrorListener { Toast.makeText(appContext, "No se pudo contactar al servidor. Intentar mas tarde.", Toast.LENGTH_SHORT).show() })

        queue.add(stringRequest)
    }

    fun ConseguirReservacion(appContext:Context, userId:Int) {
        val queue = Volley.newRequestQueue(appContext)


        val url = "$urlApi/Reservacion/GetAll"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val gson = Gson()
                val reservacionItem: Array<ReservacionItem> = gson.fromJson(response, Array<ReservacionItem>::class.java)
                Toast.makeText(appContext, "Comunicacion Exitosa!", Toast.LENGTH_SHORT).show()
                /*Quitar antes de la entrega el toast*/
            },
            Response.ErrorListener { Toast.makeText(appContext, "No se pudo contactar al servidor. Intentar mas tarde.", Toast.LENGTH_SHORT).show() })

        queue.add(stringRequest)
    }

    fun ConseguirPais(appContext:Context, userId:Int) {
        val queue = Volley.newRequestQueue(appContext)


        val url = "$urlApi/Pais/GetAll"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val gson = Gson()
                val paisItem: Array<PaisItem> = gson.fromJson(response, Array<PaisItem>::class.java)
                Toast.makeText(appContext, "Comunicacion Exitosa!", Toast.LENGTH_SHORT).show()
                /*Quitar antes de la entrega el toast*/
            },
            Response.ErrorListener { Toast.makeText(appContext, "No se pudo contactar al servidor. Intentar mas tarde.", Toast.LENGTH_SHORT).show() })

        queue.add(stringRequest)
    }


}