package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
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
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


class addPet : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_createpet);

        val ldb = LocalDatabaseManager(this)

        val pickImage = 100
        var imageUri: Uri? = null

        val pbAgregarMascota = findViewById<ProgressBar>(R.id.pb_agregar_mascota)
        val spinnerTipoMascotas = findViewById<Spinner>(R.id.spinner_tipo)
        val spinnerTamano = findViewById<Spinner>(R.id.spinner_tamano)
        val btnRegistrar = findViewById<Button>(R.id.btn_registrar_mascota)
        val btnRegresar = findViewById<ImageView>(R.id.btn_addpet_regresar)


        val tbNombre = findViewById<TextView>(R.id.tv_nombre_mascota)
        val tbEdad = findViewById<TextView>(R.id.tv_edad_mascota)

        val btnFotoMascota =  findViewById<Button>(R.id.btn_foto_mascota)
        val btnFotoAlimento = findViewById<Button>(R.id.btn_foto_alimento)
        val btnFotoCarnet = findViewById<Button>(R.id.btn_foto_carnet)

        pbAgregarMascota.visibility=View.GONE

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

        spinnerTamano.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val tamano = parent?.getItemAtPosition(position)
                if(tamano.toString() == "Tiny")
                    tamanoMascotaResult = 1
                else if(tamano.toString()=="Pequeño")
                    tamanoMascotaResult = 2
                else if(tamano.toString()=="Mediano")
                    tamanoMascotaResult = 3
                else if(tamano.toString()=="Grande")
                    tamanoMascotaResult=4
                else if(tamano.toString() == "Gigante")
                    tamanoMascotaResult=5
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("onNothing","Nothing selected")
            }

        }

        btnRegistrar.setOnClickListener {

            runOnUiThread {
                pbAgregarMascota.visibility=View.VISIBLE
            }
                val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
                val idString = prefs.getString("id","Unknown")

                //Toast.makeText(applicationContext, "Se esta subiendo a la nube su mascota, espere un momento...", Toast.LENGTH_SHORT).show()

                var error=false;

            //Validaciones

                if(!error){

                    val imagenPetIV = this.imagemascota
                    val imagenCarnet = this.imagecarnet
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
                        tipoMascotaResult,
                        tamanoMascotaResult,
                        imageString,
                        imageString2,
                        imgAlimento,
                        "2021-06-03T18:30:40.299Z")


                    var url="https://patoparra.com/api/mascota/create"

                    request.setPet(url,mascotaNueva,object: Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            println("Failure to save pet")

                            runOnUiThread {
                                pbAgregarMascota.visibility=View.GONE
                                Toast.makeText(applicationContext,e.toString(), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val responseData = response.body()?.string()
                            Log.d("reqSuccess",responseData.toString())
                            ldb.InsertPet(mascotaNueva,true)
                            runOnUiThread {
                                pbAgregarMascota.visibility=View.GONE
                                Toast.makeText(applicationContext,"Se agrego su mascota de forma exitosa!", Toast.LENGTH_SHORT).show()
                            }
                            val registerActivity = Intent(applicationContext, MisMascotasActivity::class.java)
                            startActivity(registerActivity)

                        }

                    })
                }

        }

        btnFotoMascota.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 1)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        btnFotoAlimento.setOnClickListener {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePicture,3)
            }catch (e:ActivityNotFoundException){

            }
        }

        btnFotoCarnet.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        btnRegresar.setOnClickListener {
            this.finish()
        }
    }

    fun resizeImg(image:Bitmap):Bitmap{
        val width = image.width
        val height = image.height

        val scaleW  = width/2
        val scaleH = width/2

        return Bitmap.createScaledBitmap(image, scaleW, scaleH, true)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()
            println("size: ${imageBitmap.byteCount}")
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            var compressedImg = BitmapFactory.decodeStream(ByteArrayInputStream(stream.toByteArray()))
            compressedImg = resizeImg(compressedImg)
            println("compress: ${compressedImg.byteCount}")
            imagemascota.setImageBitmap(compressedImg)
        }
        else if (resultCode == RESULT_OK && requestCode == 100) {
            var imageUri: Uri? = data?.data

            this.imagecarnet.setImageURI(imageUri)
        }
        else if(resultCode  == RESULT_OK && requestCode == 3){
            val imgbm = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()

            imgbm.compress(Bitmap.CompressFormat.JPEG,80,stream)

            imgalimento.setImageBitmap(imgbm)
            //findViewById<ImageView>(R.id.imgalimento).setImageBitmap(imgbm)
        }
    }


    }
