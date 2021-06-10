package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.Models.FullPetModel
import com.example.myapplication.Models.Mascota
import com.example.myapplication.Models.UserModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.layout_createpet.*
import kotlinx.android.synthetic.main.layout_petinfo.*
import kotlinx.android.synthetic.main.list_template.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class PetInfiActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_petinfo)

        //spinner_tipo_Pet
        val tipoSPN = findViewById<Spinner>(R.id.spinner_tipo_Pet)
        val tamanoSPN = findViewById<Spinner>(R.id.spinner_tamano_Pet)
        val nameET = findViewById<TextView>(R.id.editTextTextPersonName6)
        val ageET = findViewById<TextView>(R.id.editTextTextPersonName7)
        val pbPetInfo = findViewById<ProgressBar>(R.id.progressBar2)
        val btnFoto = findViewById<Button>(R.id.btn_subirFoto)
        val btnAlimento = findViewById<Button>(R.id.subir_alimento)
        val btnPDF = findViewById<Button>(R.id.subir_pdf)
        val btnEditar = findViewById<Button>(R.id.editaPetInfo)

        btnFoto.visibility = View.INVISIBLE
        btnPDF.visibility = View.INVISIBLE
        btnAlimento.visibility = View.INVISIBLE

        nameET.isEnabled = false
        ageET.isEnabled = false

        tipoSPN.isEnabled = false
        tamanoSPN.isEnabled=false




        pbPetInfo.visibility = View.VISIBLE

        val petName = intent.getStringExtra("PET_NAME")
        val petId = intent.getIntExtra("PET_ID",0)

        val encoder = ImageParser()


        var client = OkHttpClient()
        var request = OkHttpRequest(client)

        val url ="https://patoparra.com/api/Mascota/Get/$petId"

        println(petId)


        val imageIV =this.imageView7
        val carnetIV = this.imageView10
        val foodIV = this.imageView12


        request.getPetById(url,object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    pbPetInfo.visibility = View.GONE
                    Toast.makeText(applicationContext, "No se pudo obtener información de la mascota. verifique su conexión",Toast.LENGTH_LONG).show()
                }
            }

            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call, response: Response) {
                val responseData =  response.body()?.string()
                val jsonArray = JSONArray(responseData)
                val jsonObject = jsonArray.getJSONObject(0)

                println(jsonObject)

                val nombrePet =jsonObject.get("nombre").toString()
                val edadPet =jsonObject.get("edad").toString()
                val tipoPet =jsonObject.get("tipoMascotaId").toString().toInt()
                val tamanoPet =jsonObject.get("categoriaMascotaId").toString().toInt()
                val imagenPet = jsonObject.get("imagen").toString()
                val comidaPet = jsonObject.get("alimento").toString()
                val carnetPet = jsonObject.get("cartillaPdf").toString()


                runOnUiThread {
                    pbPetInfo.visibility = View.GONE
                    nameET.setText(nombrePet)
                    ageET.setText(edadPet)
                    imageIV.setImageBitmap(imagenPet?.let { encoder.convert(it) })
                    carnetIV.setImageBitmap(carnetPet?.let { encoder.convert(it) })
                    foodIV.setImageBitmap(comidaPet?.let{encoder.convert(it) })
                    println("Tamano $tamanoPet")
                    println("Tipo $tipoPet")
                    tipoSPN.setSelection(tipoPet-1)
                    tamanoSPN.setSelection(tamanoPet-1)
                }

            }

        })


      /*  //Ejecutar en caso de no tener conexion a la base de datos
        val ldb = LocalDatabaseManager(applicationContext)

        val arrayList = petName?.let { ldb.GetPetInfo(it) }

        val petSelected = arrayList?.get(0)

        if (petSelected != null) {

            this.editTextTextPersonName6.setText(petSelected.nombre)
            this.editTextTextPersonName7.setText(petSelected.edad.toString())
            this.imageView7.setImageBitmap(petSelected.imagen?.let { encoder.convert(it) })
            this.imageView10.setImageBitmap(petSelected.cartillaPdf?.let { encoder.convert(it) })
            this.imageView12.setImageBitmap(petSelected.alimento?.let{encoder.convert(it) })


        }
        //=--------------------------------------------------*/

        var mLastClickTime = 0.0

        var doublePressPrev = false

        this.button9.setOnClickListener(){
            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble();

            if(!doublePressPrev){
                finish()
            }
        }

        var enEdicion = false

        btnEditar.setOnClickListener{
            enEdicion = !enEdicion
            if(enEdicion){
                btnFoto.visibility = View.VISIBLE
                btnPDF.visibility = View.VISIBLE
                btnAlimento.visibility = View.VISIBLE

                btnEditar.text = "Guardar"

                nameET.isEnabled = true
                ageET.isEnabled = true

                tipoSPN.isEnabled = true
                tamanoSPN.isEnabled=true


            }else{
                //Enviar peticion de actualizacion de informacion
                pbPetInfo.visibility = View.VISIBLE

                var tipoMascotaResult:Int=0
                var tamanoMascotaResult:Int=0

                tipoSPN.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                    ) {
                        val tipoPet = parent?.getItemAtPosition(position)
                        if(tipoPet.toString() == "Perros")
                            tipoMascotaResult=1
                        else if(tipoPet.toString() == "Gatos")
                            tipoMascotaResult=2
                        else  if(tipoPet.toString() == "Peces")
                            tipoMascotaResult=3
                        else
                            tipoMascotaResult=4
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Log.d("onNothing","Nothing selected")
                    }

                }

                val url2 ="https://patoparra.com/api/Mascota/Get/$petId"

                val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)

                val idString =  prefs.getString("id","Unknkown")


                val encoder = ImageParser()
                var bitmap = imageView7.drawable.toBitmap()
                var bitmap2 = imageView10.drawable.toBitmap()
                var bitmap3 = imageView12.drawable.toBitmap()
                val imageString = encoder.convert(bitmap)
                val imageString2 = encoder.convert(bitmap2)
                val imageString3 = encoder.convert(bitmap3)

                val mascotaNueva = Mascota(idString,nameET.text.toString(), ageET.text.toString().toInt(),0,0,imageString,imageString2,imageString3,"2021-06-03T18:30:40.299Z")

                request.updatePet(url, mascotaNueva,object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        TODO("Not yet implemented")
                    }


                })

                btnFoto.visibility = View.INVISIBLE
                btnPDF.visibility = View.INVISIBLE
                btnAlimento.visibility = View.INVISIBLE

                nameET.isEnabled = false
                ageET.isEnabled = false

                tipoSPN.isEnabled = false
                tamanoSPN.isEnabled=false

                btnEditar.text = "Editar Info"

            }

        }



/*        this.button11.setOnClickListener(){
            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble()

            if(!doublePressPrev){
                finish()
            }
        }*/


    }

}