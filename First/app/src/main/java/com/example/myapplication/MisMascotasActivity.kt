package com.example.myapplication

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapters.CustomAdapter
import com.example.myapplication.Models.PetModel
import kotlinx.android.synthetic.main.layout_pet.*
import kotlinx.android.synthetic.main.layout_reservacion_checkout.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class MisMascotasActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_pet)

        var client = OkHttpClient()
        var request = OkHttpRequest(client)
        val url ="https://patoparra.com/api/cliente/getmascotas/1"


        request.getPets(url, object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"no se pudo obtener informacion de las mascotas",Toast.LENGTH_LONG).show()
                }

            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                var listaMascotas = ArrayList<PetModel>()
                runOnUiThread {
                    try{
                        val json = JSONObject(responseData)
                        val mascotasJson = json.getJSONArray("mascotas")
                        var index=0;
                        for(i in 0..mascotasJson.length()-1){
                            val nombre = mascotasJson.getJSONObject(i).getString("nombre")
                            val tipo = mascotasJson.getJSONObject(i).getString("tipo")
                            val tamano = mascotasJson.getJSONObject(i).getString("tamano")

                            val mascota = PetModel(nombre,tipo,tamano, R.drawable.dog_dos)

                            listaMascotas.add(mascota)

                        }

                        val lista = findViewById<ListView>(R.id.lista)
                        val adaptador = CustomAdapter(applicationContext, listaMascotas)

                        lista.adapter = adaptador

                        lista.onItemClickListener = AdapterView.OnItemClickListener{parent, view,  position, id ->
                            Toast.makeText(applicationContext,listaMascotas.get(id.toInt()).toString(),Toast.LENGTH_LONG).show()
                        }

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }

        })

        val btnRegistrarMascota = findViewById<Button>(R.id.btn_registro_mascota)
        btnRegistrarMascota.setOnClickListener {
            val registerActivity = Intent(applicationContext, addPet::class.java)
            startActivity(registerActivity)
        }
    }
}