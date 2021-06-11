package com.example.myapplication

import android.app.DialogFragment
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.Models.Mascota
import kotlinx.android.synthetic.main.layout_createpet.*
import kotlinx.android.synthetic.main.layout_petinfo.*
import kotlinx.android.synthetic.main.list_template.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


class PetInfiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_petinfo)

        val pbPetInfo = findViewById<ProgressBar>(R.id.pb_editar_mascota)
        val nameET = findViewById<TextView>(R.id.editTextTextPersonName6)
        val ageET = findViewById<TextView>(R.id.editTextTextPersonName7)
        val tipoSPN = findViewById<Spinner>(R.id.spinner_tipo_Pet)
        val tamanoSPN = findViewById<Spinner>(R.id.spinner_tamano_Pet)
        val btnFoto = findViewById<Button>(R.id.btn_subirFoto)
        val btnAlimento = findViewById<Button>(R.id.subir_alimento)
        val btnPDF = findViewById<Button>(R.id.subir_pdf)
        val btnEditar = findViewById<Button>(R.id.editaPetInfo)
        val btnRegresar = findViewById<ImageView>(R.id.btn_editarpet_regresar)
        val btnEliminar =findViewById<Button>(R.id.btn_eliminar_mascota)


        nameET.isEnabled = false
        ageET.isEnabled = false
        tipoSPN.isEnabled = false
        tamanoSPN.isEnabled=false
        btnFoto.visibility = View.INVISIBLE
        btnPDF.visibility = View.INVISIBLE
        btnAlimento.visibility = View.INVISIBLE
        pbPetInfo.visibility = View.VISIBLE

        val petName = intent.getStringExtra("PET_NAME")
        val petId = intent.getIntExtra("PET_ID", 0)

        val encoder = ImageParser()

        val imageIV =this.imageView7
        val carnetIV = this.imageView10
        val foodIV = this.imageView12


        var client = OkHttpClient()
        var request = OkHttpRequest(client)


        val url ="https://patoparra.com/api/Mascota/Get/$petId"
        var tipoPet =0
        var tamanoPet =0
        request.getPetById(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    pbPetInfo.visibility = View.GONE
                    Toast.makeText(applicationContext, "No se pudo obtener información de la mascota. verifique su conexión", Toast.LENGTH_LONG).show()
                }
            }

            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                val jsonArray = JSONArray(responseData)
                val jsonObject = jsonArray.getJSONObject(0)

                println(jsonObject)

                val nombrePet = jsonObject.get("nombre").toString()
                val edadPet = jsonObject.get("edad").toString()
                tipoPet = jsonObject.get("tipoMascotaId").toString().toInt()
                tamanoPet = jsonObject.get("categoriaMascotaId").toString().toInt()
                val imagenPet = jsonObject.get("imagen").toString()
                val comidaPet = jsonObject.get("alimento").toString()
                val carnetPet = jsonObject.get("cartillaPdf").toString()


                runOnUiThread {
                    pbPetInfo.visibility = View.GONE
                    nameET.setText(nombrePet)
                    ageET.setText(edadPet)
                    imageIV.setImageBitmap(imagenPet?.let { encoder.convert(it) })
                    carnetIV.setImageBitmap(carnetPet?.let { encoder.convert(it) })
                    foodIV.setImageBitmap(comidaPet?.let { encoder.convert(it) })
                    println("Tamano $tamanoPet")
                    println("Tipo $tipoPet")
                    tipoSPN.setSelection(tipoPet - 1)
                    tamanoSPN.setSelection(tamanoPet - 1)
                }

            }

        })


        var tipoMascotaResult = tipoPet
        var tamanoMascotaResult = tamanoPet

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

            }

        }

        tamanoSPN.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
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
                Log.d("onNothing", "Nothing selected")
            }

        }

        btnPDF.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 1)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        btnFoto.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 2)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        btnAlimento.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 3)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        btnEliminar.setOnClickListener {
            runOnUiThread {
                pbPetInfo.visibility=View.VISIBLE
            }

            val alertDialog: AlertDialog? = this?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("Eliminar Mascota").setMessage("¿Estás seguro de eliminar la mascota?").apply {
                    setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User clicked OK button
                                val urlEliminar = "https://patoparra.com/api/Mascota/Delete?id=$petId"
                                request.deletePet(urlEliminar, object : Callback {
                                   override fun onFailure(call: Call, e: IOException) {
                                       runOnUiThread {
                                           pbPetInfo.visibility = View.GONE
                                       }
                                       Log.d("on failure msg", e.message.toString())
                                   }

                                   override fun onResponse(call: Call, response: Response) {
                                       val responseData = response.body()?.toString();
                                       runOnUiThread {
                                           runOnUiThread {
                                               pbPetInfo.visibility = View.GONE
                                               Toast.makeText(applicationContext, "Eliminado correctamente", Toast.LENGTH_SHORT).show()
                                           }
                                           finish()
                                       }
                                   }

                               })
                            })
                    setNegativeButton("Cancelar",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                            })
                }

                // Create the AlertDialog
                builder.create()
            }
            alertDialog?.show()






        }


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

        var enEdicion = false

        //Habilitar / Deshabilitar Edición de Mascota
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





                val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
                val idString =  prefs.getString("id", "Unknkown")


                val encoder = ImageParser()
                var bitmap = imageView7.drawable.toBitmap()
                var bitmap2 = imageView10.drawable.toBitmap()
                var bitmap3 = imageView12.drawable.toBitmap()
                val imageString = encoder.convert(bitmap)
                val imageString2 = encoder.convert(bitmap2)
                val imageString3 = encoder.convert(bitmap3)

                val mascotaNueva = Mascota(
                        idString,
                        nameET.text.toString(),
                        ageET.text.toString().toInt(),
                        tipoMascotaResult,
                        tamanoMascotaResult,
                        imageString,
                        imageString2,
                        imageString3,
                        "2021-06-03T18:30:40.299Z")

                Log.d("mascotaNueva", mascotaNueva.toString())

                val url2 ="https://patoparra.com/api/Mascota/Put/$petId"
                request.updatePet(url2, mascotaNueva, object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            pbPetInfo.visibility = View.GONE
                            println(e.message)
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            Log.d("response on update", response.toString())
                            pbPetInfo.visibility = View.GONE
                            Toast.makeText(applicationContext, "Mascota actualizada", Toast.LENGTH_SHORT).show()
                        }
                        finish();
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

        //Regresar a ventana previa
        btnRegresar.setOnClickListener {
            val registerActivity = Intent(applicationContext, MisMascotasActivity::class.java)
            startActivity(registerActivity)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            var compressedImg = BitmapFactory.decodeStream(ByteArrayInputStream(stream.toByteArray()))
            imageView12.setImageBitmap(compressedImg)
        }
        else if (resultCode == RESULT_OK && requestCode == 2) {
            val imgbm = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()

            imgbm.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            imageView7.setImageBitmap(imgbm)
        }
        else if(resultCode  == RESULT_OK && requestCode == 3){
            val imgbm = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()

            imgbm.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            imageView10.setImageBitmap(imgbm)
        }
    }

}