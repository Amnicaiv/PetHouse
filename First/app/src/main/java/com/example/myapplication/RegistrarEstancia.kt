package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.Models.HouseModel
import kotlinx.android.synthetic.main.layout_createpet.*
import kotlinx.android.synthetic.main.layout_registroresidencia.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class RegistrarEstancia :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registroresidencia)

        val btnFoto1 = findViewById<Button>(R.id.btn_agregar_imagen)
        val btnFoto2 = findViewById<Button>(R.id.btn_agregar_imagen2)
        val btnFoto3 = findViewById<Button>(R.id.btn_agregar_imagen3)
        val img1 = findViewById<ImageView>(R.id.iv_imagen1)
        val img2 = findViewById<ImageView>(R.id.iv_imagen2)
        val img3 = findViewById<ImageView>(R.id.iv_imagen3)
        val pbRegistrarHogar = findViewById<ProgressBar>(R.id.pb_registrar_hogar)
        val btnRegresar = findViewById<ImageView>(R.id.btn_registrar_hogar_regresar)

        pbRegistrarHogar.visibility=View.GONE

        btnFoto1.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 1)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }
        btnFoto2.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 2)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }
        btnFoto3.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 3)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        this.btn_enviar_solicitud.setOnClickListener(){
            runOnUiThread {
                pbRegistrarHogar.visibility= View.VISIBLE
                btnRegresar.isEnabled=false
            }

            val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
            val idString = prefs.getString("id","Unknown")

            val descripcion = this.tb_descripcion.text.toString()
            val costoxNoche = this.editTextNumber.text.toString().toDouble()
            val capacidad = this.tb_capacidad.text.toString().toInt()
            val cuentaMascotas = this.checkBox3.isChecked


            val encoder = ImageParser()

            img1.invalidate()
            var bitmap = img1.drawable.toBitmap()
            val img1String = encoder.convert(bitmap)

            img2.invalidate()
            bitmap = img2.drawable.toBitmap()
            val img2String = encoder.convert(bitmap)

            img3.invalidate()
            bitmap = img3.drawable.toBitmap()
            val img3String = encoder.convert(bitmap)

            val casaNueva = HouseModel(
                    idString,
                    descripcion,
                    costoxNoche,
                    capacidad,
                    cuentaMascotas,
                    true,
                    true,
                    false,
                    "2021-06-04T15:26:27.083Z",
                    img1String,
                    img2String,
                    img3String)

            println(casaNueva)

            var client = OkHttpClient()
            var request = OkHttpRequest(client)

            var url="https://patoparra.com/api/Hogar/Create"

            request.setHouse(url,casaNueva,object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Log.d("on failure response", e.message.toString())
                        pbRegistrarHogar.visibility=View.GONE
                        Toast.makeText(applicationContext, e.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body()?.string()
                    Log.d("response casa", response.toString())
                    runOnUiThread {
                        Log.d("on success response", response.toString())
                        pbRegistrarHogar.visibility=View.GONE
                        Toast.makeText(applicationContext, responseData.toString(), Toast.LENGTH_SHORT).show()
                    }
                    Log.d("reqSuccess",responseData.toString())
                    finish()
                }

            })

        }

        btnRegresar.setOnClickListener {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,80,stream)
            var compressedImg = BitmapFactory.decodeStream(ByteArrayInputStream(stream.toByteArray()))
            iv_imagen1.setImageBitmap(compressedImg)
        }
        else if(resultCode  == RESULT_OK && requestCode == 2){
            val imgbm = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()

            imgbm.compress(Bitmap.CompressFormat.JPEG,80,stream)

            iv_imagen2.setImageBitmap(imgbm)
            //findViewById<ImageView>(R.id.imgalimento).setImageBitmap(imgbm)
        }
        else if(resultCode  == RESULT_OK && requestCode == 3){
            val imgbm = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()

            imgbm.compress(Bitmap.CompressFormat.JPEG,80,stream)

            iv_imagen3.setImageBitmap(imgbm)
            //findViewById<ImageView>(R.id.imgalimento).setImageBitmap(imgbm)
        }
    }
}