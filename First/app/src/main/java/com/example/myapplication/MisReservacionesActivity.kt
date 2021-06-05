package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapters.CustomAdapter
import com.example.myapplication.Models.MiReservacionModel
import com.example.myapplication.Models.PetModel
import com.example.myapplication.Models.ReservacionModel
import kotlinx.android.synthetic.main.layout_misreservaciones.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class MisReservacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        val btnRegresar = findViewById<Button>(R.id.btn_back)
        setContentView(R.layout.layout_misreservaciones);

        val userToken = this.getSharedPreferences("key",0)
        val idString = userToken.getString("id","No id found")

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
                var listaReservaciones = ArrayList<MiReservacionModel>()
                runOnUiThread {
                    try{
                        val json = JSONObject(responseData.toString())
                        val reservJson = json.getJSONArray("reservaciones")
                        var index=0;
                        for(i in 0..reservJson.length()-1){
                            val id = reservJson.getJSONObject(i).getString("id").toInt()
                            val hogarId = reservJson.getJSONObject(i).getString("hogarId").toInt()
                            val hogarNombre = reservJson.getJSONObject(i).getString("hogarNombre")
                            val propietariaNombre = reservJson.getJSONObject(i).getString("propietariaNombre")
                            val fechaEntrada =reservJson.getJSONObject(i).getString("fechaEntrada")
                            val fechaSalida = reservJson.getJSONObject(i).getString("fechaSalida")
                            val montoTotal = reservJson.getJSONObject(i).getString("montoTotal").toInt()
                            val comentarios = reservJson.getJSONObject(i).getString("comentarios")
                            val estatus = reservJson.getJSONObject(i).getString("estatus")

                            val reserv = MiReservacionModel(id,hogarId,hogarNombre,propietariaNombre,fechaEntrada, fechaSalida, montoTotal, comentarios, estatus)

                            listaReservaciones.add(reserv)

                            Log.d("reqSuccess",reserv.hogarNombre)

                        }


                      /*  val lista = findViewById<ListView>(R.id.lista)
                        val adaptador = CustomAdapter(applicationContext, listaReservaciones)

                        lista.adapter = adaptador

                        lista.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
                            Toast.makeText(applicationContext,listaReservaciones.get(id.toInt()).toString(),Toast.LENGTH_LONG).show()
                        }*/

                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

        })



        /*val reservacionItem1= ReservacionItem(nombre = "Casa perrito sano",  fechaEntrada  = "3/17", fechaSalida ="3/20", foto =  1)
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

        val adapter = MisReservacionesAdapter(this, listaReservaciones as ArrayList<ReservacionItem>)
        listView.adapter = adapter*/

        this.btn_back.setOnClickListener(){
            finish()
        }

    }
}