package com.example.myapplication

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapters.CustomAdapter
import com.example.myapplication.Models.FullPetModel
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

        val ldb = LocalDatabaseManager(applicationContext)

        val userToken = this.getSharedPreferences("key",0)
        val idString = userToken.getString("id","No id found")

        var client = OkHttpClient()
        var request = OkHttpRequest(client)
        val url ="https://patoparra.com/api/cliente/getmascotas/?id=$idString"


        request.getPets(url, object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    //Toast.makeText(applicationContext,"no se pudo obtener informacion de las mascotas",Toast.LENGTH_LONG).show()
                    val petsList = ldb.GetUserPets()
                    var listaMascotas = ArrayList<PetModel>()

                    for(i in 0 until petsList.size){
                        //Toast.makeText(applicationContext, "Hello there", Toast.LENGTH_SHORT).show()
                        val nombre = petsList[i].nombre
                       /* val tipo = petsList[i].tipoMascotaId
                        val tamano = petsList[i].categoriaMascotaId*/

                        val tipo = petsList[i].tipoMascotaId.toString()
                        val tamano = petsList[i].categoriaMascotaId.toString()

                        val mascota = PetModel(nombre,tipo,tamano, R.drawable.dog_dos)

                        listaMascotas.add(mascota)
                    }

                    val lista = findViewById<ListView>(R.id.lista)
                    val adaptador = CustomAdapter(applicationContext, listaMascotas)

                    lista.adapter = adaptador

                    lista.onItemClickListener = AdapterView.OnItemClickListener{parent, view,  position, id ->

                        Toast.makeText(applicationContext,listaMascotas.get(id.toInt()).nombre ,Toast.LENGTH_LONG).show()
                        val registerActivity = Intent(applicationContext, PetInfiActivity::class.java)
                        registerActivity.putExtra("PET_NAME", listaMascotas[id.toInt()].nombre)
                        startActivity(registerActivity)
                        //intent.putExtra("EXTRA_SESSION_ID", sessionId);
                    }

                    
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

                            Toast.makeText(applicationContext,listaMascotas.get(id.toInt()).nombre ,Toast.LENGTH_LONG).show()
                            val registerActivity = Intent(applicationContext, PetInfiActivity::class.java)
                            registerActivity.putExtra("PET_NAME", listaMascotas[id.toInt()].nombre)
                            startActivity(registerActivity)
                            //intent.putExtra("EXTRA_SESSION_ID", sessionId);
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