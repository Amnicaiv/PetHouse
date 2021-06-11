package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_registroresidencia.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class EditarEstanciaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_editar_residencia);

        val pbLoading = findViewById<ProgressBar>(R.id.pb_editar_hogar)
        val btnRegresar  = findViewById<ImageView>(R.id.btn_editar_hogar_regresar)
        val tbDescripcion = findViewById<TextView>(R.id.tb_descripcion_edit)
        val tbCapacidad = findViewById<TextView>(R.id.tb_capacidad_edit)
        val cbCuentaMascotas = findViewById<CheckBox>(R.id.checkBox30)
        val tbPrecioPorNoche = findViewById<TextView>(R.id.editTextNumber_100)
        val tbImg1 = findViewById<ImageView>(R.id.iv_imagen1_edit)
        val tbImg2 = findViewById<ImageView>(R.id.iv_imagen2_edit)
        val tbImg3 = findViewById<ImageView>(R.id.iv_imagen3_edit)
        val btnImg1 = findViewById<Button>(R.id.btn_editar_imagen)
        val btnImg2 = findViewById<Button>(R.id.btn_editar_imagen2)
        val btnImg3 = findViewById<Button>(R.id.btn_agregar_imagen3_edit)

        btnImg1.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 1)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        btnImg2.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 1)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        btnImg3.setOnClickListener{
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