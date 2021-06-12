package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.ReservacionLista
import kotlinx.android.synthetic.main.layout_misreservaciones.*
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
        setContentView(R.layout.layout_misreservaciones);

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
        val idString = prefs.getString("id","")


        val pbReservaciones = findViewById<ProgressBar>(R.id.pb_mis_reservaciones)
        val btnRegresar = findViewById<ImageView>(R.id.btn_addpet_regresar3)
        val url = "https://patoparra.com/api/Reservacion/GetFromCliente/?clienteId=$idString"

        var client = OkHttpClient()
        var request = OkHttpRequest(client)


        //Send POST Request to server
        request.getUserReservations(url, object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    pbReservaciones.visibility= View.GONE
                    Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()
                }

            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                var listaReservaciones = ArrayList<ReservacionLista>()
                runOnUiThread {
                    try {
                        val json = JSONObject(responseData.toString())
                        val reservJson = json.getJSONArray("reservaciones")
                        var index=0;
                        for(i in 0..reservJson.length()-1) {
                            val id = reservJson.getJSONObject(i).getInt("id")
                            val hogarNombre = reservJson.getJSONObject(i).getString("hogarNombre")
                            val propietariaNombre = reservJson.getJSONObject(i).getString("propietariaNombre")
                            val montoTotal = reservJson.getJSONObject(i).getDouble("montoTotal")
                            val estatus = reservJson.getJSONObject(i).getString("estatus")
                            val fechaE = reservJson.getJSONObject(i).getString("fechaEntrada")
                            val fechaS = reservJson.getJSONObject(i).getString("fechaSalida")


                            val reservacion = ReservacionLista(id,hogarNombre,propietariaNombre,montoTotal,estatus,fechaE,fechaS)

                            listaReservaciones.add(reservacion)
                        }

                        pbReservaciones.visibility=View.GONE
                        val lista = findViewById<ListView>(R.id.LV_MisReservaciones)
                        val adaptador = MisReservacionesAdapter(applicationContext, listaReservaciones )

                        lista.adapter = adaptador

                        lista.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
                            val ticketActivity = Intent(applicationContext, Ticket::class.java)
                            ticketActivity.putExtra("RES_DESC", listaReservaciones[id.toInt()].hogarNombre )
                            ticketActivity.putExtra("RES_PROP", listaReservaciones[id.toInt()].propietariaNombre)
                            ticketActivity.putExtra("RES_TOTAL", listaReservaciones[id.toInt()].montoTotal)
                            ticketActivity.putExtra("RES_FECHAE", listaReservaciones[id.toInt()].fechaEntrada)
                            ticketActivity.putExtra("RES_FECHAS", listaReservaciones[id.toInt()].fechaSalida)
                            startActivity(ticketActivity)
                        }

                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

        })

        btnRegresar.setOnClickListener{
            finish()
        }

    }
}