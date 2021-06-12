package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.Models.SignupModel
import com.example.myapplication.Models.UpdateModel
import kotlinx.android.synthetic.main.layout_info_usuario.*
import kotlinx.android.synthetic.main.layout_petinfo.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class InformacionUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_info_usuario);

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
        val idString =  prefs.getString("id", "Unknkown")

        val tbImagenPerfil = findViewById<ImageView>(R.id.iv_imagen_perfil_edit)
        val btnImagenPerfil = findViewById<Button>(R.id.btn_profile_img_edit)
        val tbNombre = findViewById<TextView>(R.id.txt_nombre_edit)
        val tbApellidoPaterno = findViewById<TextView>(R.id.txt_apPaterno_edit)
        val tbApellidoMaterno = findViewById<TextView>(R.id.txt_apMaterno_edit)
        val spinnerDia = findViewById<Spinner>(R.id.spinner_dia_edit)
        val spinnerMes = findViewById<Spinner>(R.id.spinner_mes_edit)
        val spinnerAnio = findViewById<Spinner>(R.id.spinner_anio_edit)
        val tbDireccion = findViewById<TextView>(R.id.txt_direccion_edit)
        val tbNoExterior = findViewById<TextView>(R.id.txt_noExt_edit)
        val tbNoInterior = findViewById<TextView>(R.id.txt_noInt_edit)
        val tbCodigoPostal = findViewById<TextView>(R.id.txt_codigoPostal_edit)
        val spinnerCiudad = findViewById<Spinner>(R.id.spinner_ciudad_edit)
        val tbTelefono = findViewById<TextView>(R.id.txt_telefono_edit)
        val tbNombreUsuario = findViewById<TextView>(R.id.txt_nombreUsuario_edit)
        val tbCorreoElectronico = findViewById<TextView>(R.id.txt_correoElectronico_edit)
        val tbContrasena = findViewById<TextView>(R.id.txt_contrasena_edit)
        val tbContrasenaConfirmacion = findViewById<TextView>(R.id.txt_confirmacionContrasena_edit)
        val btnEditar = findViewById<Button>(R.id.btn_signup2)
        val pbLoading = findViewById<ProgressBar>(R.id.pb_editar_info_usuario)
        val btnRegresar = findViewById<ImageView>(R.id.btn_editarusuario_regresar)

        btnImagenPerfil.isEnabled=false;
        tbNombre.isEnabled=false;
        tbApellidoPaterno.isEnabled=false;
        tbApellidoMaterno.isEnabled =false;
        tbDireccion.isEnabled=false
        tbNoExterior.isEnabled=false
        tbNoInterior.isEnabled=false
        tbCodigoPostal.isEnabled=false
        tbTelefono.isEnabled=false
        tbNombreUsuario.isEnabled=false
        tbContrasena.isEnabled=false
        tbContrasenaConfirmacion.isEnabled=false

        var client = OkHttpClient()
        var request = OkHttpRequest(client)

        var enEdicion=false
        btnEditar.setOnClickListener {
            enEdicion = !enEdicion

            if(enEdicion)
            {
                btnEditar.setText("Guadar");
                btnImagenPerfil.isEnabled=true;
                tbNombre.isEnabled=true;
                tbApellidoPaterno.isEnabled=true;
                tbApellidoMaterno.isEnabled =true;
                tbDireccion.isEnabled=true
                tbNoExterior.isEnabled=true
                tbNoInterior.isEnabled=true
                tbCodigoPostal.isEnabled=true
                tbTelefono.isEnabled=true
                tbContrasena.isEnabled=true
                tbContrasenaConfirmacion.isEnabled=true
            }
            else
            {
                btnEditar.isEnabled=false
                pbLoading.visibility=View.VISIBLE
                val encoder = ImageParser()
                var bitmap = tbImagenPerfil.drawable.toBitmap()
                val imagen = encoder.convert(bitmap)

                val urlEdicion = "https://patoparra.com/api/Security/UpdateUser"
                val usuarioActualizado = UpdateModel(
                        idString,
                        tbNombre.text.toString(),
                        tbApellidoPaterno.text.toString(),
                        tbApellidoMaterno.text.toString(),
                        "1999-06-03T00:30:40.299Z",
                        tbDireccion.text.toString(),
                        tbNoExterior.text.toString(),
                        tbNoInterior.text.toString(),
                        tbCodigoPostal.text.toString(),
                        5,
                        tbNombreUsuario.text.toString(),
                        tbCorreoElectronico.text.toString(),
                        tbTelefono.text.toString(),
                        tbContrasena.text.toString(),imagen)
                println(usuarioActualizado)

                request.updateUser(urlEdicion, usuarioActualizado, object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()
                            btnEditar.isEnabled=true
                            btnEditar.setText("Guardar")
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()?.string()
                        println(responseData)
                        runOnUiThread {
                            btnEditar.isEnabled=true
                            btnEditar.setText("Editar")
                            pbLoading.visibility=View.GONE

                            btnImagenPerfil.isEnabled=false
                            tbNombre.isEnabled=false
                            tbApellidoPaterno.isEnabled=false
                            tbApellidoMaterno.isEnabled =false
                            tbDireccion.isEnabled=false
                            tbNoExterior.isEnabled=false
                            tbNoInterior.isEnabled=false
                            tbCodigoPostal.isEnabled=false
                            tbTelefono.isEnabled=false
                            tbContrasena.isEnabled=false
                            tbContrasenaConfirmacion.isEnabled=false
                            Toast.makeText(applicationContext,"Información actualizada",Toast.LENGTH_SHORT).show()

                            Log.d("response on update", response.toString())

                        }
                    }

                })
            }
        }



        val url = "https://patoparra.com/api/Cliente/GetFromId/$idString"



        //Send GET Request to server
        request.getUserData(url, object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    pbLoading.visibility=View.GONE
                    Toast.makeText(applicationContext,e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                runOnUiThread {
                    try{
                        pbLoading.visibility=View.GONE
                        var token = responseData.toString()

                        val json = JSONObject(responseData)


                        println(json)
                        tbNombre.setText(json.getString("nombre"))
                        tbApellidoPaterno.setText(json.getString("apellidoPaterno"))
                        tbApellidoMaterno.setText(json.getString("apellidoMaterno"))
                        tbCorreoElectronico.setText(json.getString("correoElectronico"))
                        tbDireccion.setText(json.getString("calle"))
                        tbNoExterior.setText(json.getString("noExterior"))
                        tbNoInterior.setText(json.getString("noInterior"))
                        tbTelefono.setText(json.getString("telefono"))
                        tbCodigoPostal.setText(json.getString("codigoPostal"));
                        val encoder = ImageParser()

                        val imagen = json.getString("imagePerfil")
                        tbImagenPerfil.setImageBitmap(imagen?.let { encoder.convert(it) })

                        tbNombreUsuario.setText(prefs.getString("nick",""))




                    }catch(e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

        })

        btnImagenPerfil.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 1)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        btnRegresar.setOnClickListener(){
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode  == RESULT_OK && requestCode == 1){
            val imgbm = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()

            imgbm.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            iv_imagen_perfil_edit.setImageBitmap(imgbm)
        }
    }
}