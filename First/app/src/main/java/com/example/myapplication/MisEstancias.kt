package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapters.MisEstanciasAdapter
import com.example.myapplication.Models.HouseModel
import com.example.myapplication.Models.listaHogarModel
import kotlinx.android.synthetic.main.layout_misestancias.*
import kotlinx.android.synthetic.main.layout_misreservaciones.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MisEstancias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_misestancias);

        val pbLoad = findViewById<ProgressBar>(R.id.pb_load)
        pbLoad.visibility=View.VISIBLE

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
        val url = "https://patoparra.com/api/Cliente/GetHogares?clienteId="+prefs.getString("id","")
        println(url)
        var client = OkHttpClient()
        var request = OkHttpRequest(client)

        request.getHogares(url,object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Error al conectar a la bd",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                var listaHogares = ArrayList<listaHogarModel>()
                runOnUiThread {
                    try{
                        val json = JSONObject(responseData)
                        println(json)
                        val hogaresJson = json.getJSONArray("hogares")
                        var index=0
                        for(i in 0..hogaresJson.length()-1){
                            val descripcion = hogaresJson.getJSONObject(i).getString("descripcion")
                            val costoPorNoche = hogaresJson.getJSONObject(i).getDouble("costoPorNoche")
                            val capacidad = hogaresJson.getJSONObject(i).getInt("capacidad")
                            val img = hogaresJson.getJSONObject(i).getString("fotoLista")

                            val hogar = listaHogarModel(descripcion, costoPorNoche, capacidad, img)
                            listaHogares.add(hogar)
                        }

                        val lista = findViewById<ListView>(R.id.LV_MisEstancias)
                        val adaptador = MisEstanciasAdapter(applicationContext, listaHogares)
                        lista.adapter = adaptador
                    }catch(e:Exception)
                    {
                        e.printStackTrace()
                    }
                    pbLoad.visibility=View.GONE
                }
            }

        })


        val btnRegistrarEstancia = findViewById<Button>(R.id.btn_registrar_casa)
        btnRegistrarEstancia.setOnClickListener(){
            val estancias = Intent(applicationContext, RegistrarEstancia::class.java)
            startActivity(estancias)
        }



        val listView = this.LV_MisEstancias

        listView.setOnItemClickListener  (){parent,view,position,id->

            val selectedObject = this.LV_MisEstancias.getItemAtPosition(position) as listaHogarModel
            val editarEstan = Intent(applicationContext, EditarEstanciaActivity::class.java)
            startActivity(editarEstan)
        }








    }

}