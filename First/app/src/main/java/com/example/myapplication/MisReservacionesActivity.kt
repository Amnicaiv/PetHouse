package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.ReservacionLista
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class MisReservacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)

        setContentView(R.layout.layout_misreservaciones);

        val userToken = this.getSharedPreferences("key",0)
        val idString = prefs.getString("id","")

        val url = "https://patoparra.com/api/Reservacion/GetFromCliente/?clienteId=$idString"



        var client = OkHttpClient()
        var request = OkHttpRequest(client)


        //Send POST Request to server
        request.getUserReservations(url, object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"no se pudo obtener informacion de las mascotas",Toast.LENGTH_LONG).show()
                }

            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                var listaReservaciones = ArrayList<ReservacionLista>()

                runOnUiThread {
                    try{

                        val json = JSONObject(responseData.toString())
                        val reservJson = json.getJSONArray("reservaciones")
                        var index=0;
                        for(i in 0..reservJson.length()-1){
                            val id = reservJson.getJSONObject(i).getInt("id")
                            val hogarNombre = reservJson.getJSONObject(i).getString("hogarNombre")
                            val propietariaNombre = reservJson.getJSONObject(i).getString("propietariaNombre")
                            val montoTotal = reservJson.getJSONObject(i).getDouble("montoTotal")
                            val estatus = reservJson.getJSONObject(i).getString("estatus")


                            val reservacion = ReservacionLista(id,hogarNombre,propietariaNombre,montoTotal,estatus)

                            listaReservaciones.add(reservacion)

                            Log.d("reqSuccess",reservacion.toString())

                        }


                       val lista = findViewById<ListView>(R.id.LV_MisReservaciones)
                        val adaptador = MisReservacionesAdapter(applicationContext, listaReservaciones )

                        lista.adapter = adaptador

                        lista.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
                            Toast.makeText(applicationContext,listaReservaciones.get(id.toInt()).toString(),Toast.LENGTH_LONG).show()
                        }

                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

        })


    }
}