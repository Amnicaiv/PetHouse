package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.FullPetModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.util.*

class addPet : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_createpet);

        val spinnerTipoMascotas = findViewById<Spinner>(R.id.spinner_tipo)
        val spinnerTamano = findViewById<Spinner>(R.id.spinner_tamano)
        val btnRegistrar = findViewById<Button>(R.id.btn_registrar_mascota)

        val tbNombre = findViewById<TextView>(R.id.tv_nombre_mascota)
        val tbEdad = findViewById<TextView>(R.id.tv_edad_mascota)

        val btnOpenCam =  findViewById<TextView>(R.id.btn_foto_mascota)

        ArrayAdapter.createFromResource(applicationContext,R.array.tipo_mascota_array,android.R.layout.simple_spinner_item).also {
            adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTipoMascotas.adapter = adapter
        }

        ArrayAdapter.createFromResource(applicationContext, R.array.tamano_mascota_array, android.R.layout.simple_spinner_item).also {
            adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTamano.adapter = adapter
        }
        var tipoMascotaResult:Int=0
        var tamanoMascotaResult:Int=0
        spinnerTipoMascotas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

        btnRegistrar.setOnClickListener {
            var error=false;

            if(!error){
                var client = OkHttpClient()
                var request = OkHttpRequest(client)
                val mascotaNueva = FullPetModel(
                    1,
                    tbNombre.text.toString(),
                    tbEdad.text.toString().toInt(),
                    1,
                    tipoMascotaResult.toString().toInt())

                var url="https://patoparra.com/api/mascota/create"

                request.setPet(url,mascotaNueva,object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("Failure to save pet")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()?.string()
                        Log.d("reqSuccess",responseData.toString())
                    }

                })
            }
        }

        btnOpenCam.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 1)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
            }


        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.imageView25).setImageBitmap(imageBitmap)
        }
    }


    }
