package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.Models.Mascota
import kotlinx.android.synthetic.main.layout_createpet.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException


class addPet : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_createpet);

        val ldb = LocalDatabaseManager(this)

        val pickImage = 100
        var imageUri: Uri? = null

        val spinnerTipoMascotas = findViewById<Spinner>(R.id.spinner_tipo)
        val spinnerTamano = findViewById<Spinner>(R.id.spinner_tamano)
        val btnRegistrar = findViewById<Button>(R.id.btn_registrar_mascota)
        val btnRegresar = findViewById<ImageView>(R.id.btn_addpet_regresar)

        val tbNombre = findViewById<TextView>(R.id.tv_nombre_mascota)
        val tbEdad = findViewById<TextView>(R.id.tv_edad_mascota)

        val btnOpenCam =  findViewById<TextView>(R.id.btn_foto_mascota)
        val btnOpenCamAlimento = findViewById<Button>(R.id.btn_foto_alimento)

        btnRegresar.setOnClickListener {
            this.finish()
        }

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
            val userToken = this.getSharedPreferences("key",0)
            val idString = userToken.getString("id","No id found")

            Toast.makeText(applicationContext, "Se esta subiendo a la nube su mascota, espere un momento...", Toast.LENGTH_SHORT).show()

            var error=false;

            if(!error){

                val imagenPetIV = this.imageView25
                val imagenCarnet = this.imageView31
                val imagenAlimento = this.imgalimento

                imagenPetIV.invalidate()
                var bitmap = imagenPetIV.drawable.toBitmap()

                val encoder = ImageParser()

                val imageString = encoder.convert(bitmap)

                imagenCarnet.invalidate()
                bitmap = imagenCarnet.drawable.toBitmap()
                val imageString2 = encoder.convert(bitmap)

                imagenAlimento.invalidate()
                bitmap = imagenAlimento.drawable.toBitmap()
                val imgAlimento = encoder.convert(bitmap)



                var client = OkHttpClient()
                var request = OkHttpRequest(client)
                val mascotaNueva = Mascota(
                    idString,
                    tbNombre.text.toString(),
                    tbEdad.text.toString().toInt(),
                    1,
                    tipoMascotaResult.toString().toInt(),
                    imageString,
                    imageString2,
                    imgAlimento,
                    "2021-06-03T18:30:40.299Z")


                var url="https://patoparra.com/api/mascota/create"

                request.setPet(url,mascotaNueva,object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("Failure to save pet")

                        runOnUiThread {
                            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()?.string()
                        Log.d("reqSuccess",responseData.toString())
                        ldb.InsertPet(mascotaNueva,true)
                        runOnUiThread {
                            Toast.makeText(applicationContext,"Se agrego su mascota de forma exitosa!", Toast.LENGTH_LONG).show()
                        }
                        val registerActivity = Intent(applicationContext, MisMascotasActivity::class.java)
                        startActivity(registerActivity)

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

        btnOpenCamAlimento.setOnClickListener {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePicture,3)
            }catch (e:ActivityNotFoundException){

            }
        }


        btn_foto_carnet.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }


        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.imageView25).setImageBitmap(imageBitmap)

        }
        if (resultCode == RESULT_OK && requestCode == 100) {
            var imageUri: Uri? = data?.data
            this.imageView31.setImageURI(imageUri)
        }
        if(requestCode == 3 && resultCode  == RESULT_OK){
            val imgbm = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.imgalimento).setImageBitmap(imgbm)
        }
    }


    }
