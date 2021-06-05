package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapters.HogaresDisponiblesAdapter
import com.example.myapplication.Models.listaHogaresDisponiblesModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class HogaresDisponiblesActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hogaresdisponibles);

        val spinnerHogares = findViewById<ProgressBar>(R.id.pb_loaing)

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
        val idString = prefs.getString("id","")
        var client = OkHttpClient()
        var request = OkHttpRequest(client)
        val url ="https://patoparra.com/api/Hogar/Get?userId=$idString"
        println(url)

        request.getHogares(url, object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    spinnerHogares.visibility=View.GONE
                    Toast.makeText(applicationContext,"error con servidor",Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                var listaHogares = ArrayList<listaHogaresDisponiblesModel>()
                runOnUiThread {
                    try{
                        val json = JSONArray(responseData)
                        val hogaresDisponibles = json
                        var index=0
                        for(i in 0..hogaresDisponibles.length()-1){
                            val map = HashMap<String, String>()
                            val e = json.getJSONObject(i)

                            map.put("id",e.getString("id"))
                            map.put("descripcion",e.getString("descripcion"))
                            map.put("costoPorNoche",e.getString("costoPorNoche"))
                            map.put("capacidad",e.getString("capacidad"))
                            map.put("nombreDueno",e.getString("nombreDueno"))


                            val hogar = listaHogaresDisponiblesModel(e.getInt("id"),e.getString("descripcion"),e.getDouble("costoPorNoche"), e.getInt("capacidad"), e.getString("nombreDueno"))
                            println(hogar)

                            listaHogares.add(hogar)
                        }

                        val lista = findViewById<ListView>(R.id.lv_hogaresdispo)
                        val adapter = HogaresDisponiblesAdapter(applicationContext,listaHogares)
                        lista.adapter  = adapter
                        spinnerHogares.visibility=View.GONE
                    }catch(e:Exception)
                    {
                        runOnUiThread {
                            spinnerHogares.visibility=View.GONE
                            Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show()
                            println(e.toString())
                        }
                    }
                }
            }

        })




    }
}

