package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.Models.SignupModel
import kotlinx.android.synthetic.main.layout_createpet.*
import kotlinx.android.synthetic.main.layout_signup.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class SignUpActivity: AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        val ldb = LocalDatabaseManager(this)

        val tbNombre = findViewById<TextView>(R.id.txt_nombre)
        val tbApellidoPaterno = findViewById<TextView>(R.id.txt_apPaterno)
        val tbApellidoMaterno = findViewById<TextView>(R.id.txt_apMaterno)
        val tbDireccion =  findViewById<TextView>(R.id.txt_direccion)
        val tbNoExterior = findViewById<TextView>(R.id.txt_noExt)
        val tbNoInterior = findViewById<TextView>(R.id.txt_noInt)
        val tbCodigoPostal = findViewById<TextView>(R.id.txt_codigoPostal)
        val tbTelefono = findViewById<TextView>(R.id.txt_telefono)
        val tbNombreUsuario = findViewById<TextView>(R.id.txt_nombreUsuario)
        val tbCorreoElectronico = findViewById<TextView>(R.id.txt_correoElectronico)
        val tbContrasena = findViewById<TextView>(R.id.txt_contrasena)
        val tbConfirmacionContrasena = findViewById<TextView>(R.id.txt_confirmacionContrasena)
        val imgPerfil = findViewById<ImageView>(R.id.profile_img)
        val btnImgPerfil = findViewById<Button>(R.id.btn_profile_img)

        val spinnerDiaFechaNacimiento = findViewById<Spinner>(R.id.spinner_dia)
        val spinnerMesFechaNacimiento = findViewById<Spinner>(R.id.spinner_mes)
        val spinnerAnioFechaNacimiento = findViewById<Spinner>(R.id.spinner_anio)
        val spinnerCiudad = findViewById<Spinner>(R.id.spinner_ciudad)

        val errorGeneral = findViewById<TextView>(R.id.txt_error_general)
        val errorNombre = findViewById<TextView>(R.id.tv_error_nombre)
        val errorApPaterno = findViewById<TextView>(R.id.tv_error_apPaterno)
        val errorApMaterno = findViewById<TextView>(R.id.tv_error_apMaterno)
        val errorFechaNacimiento = findViewById<TextView>(R.id.tv_error_fechaNacimiento)
        val errorDireccion = findViewById<TextView>(R.id.tv_error_direccion)
        val errorCodigoPostal = findViewById<TextView>(R.id.tv_error_codigoPostal)
        val errorCiudad = findViewById<TextView>(R.id.tv_error_ciudad)
        val errorTelefono = findViewById<TextView>(R.id.tv_error_telefono)
        val errorNombreUsuario = findViewById<TextView>(R.id.tv_error_nombreUsuario)
        val errorCorreoElectronico = findViewById<TextView>(R.id.tv_error_correoElectronico)
        val errorContrasena = findViewById<TextView>(R.id.tv_error_contrasena)
        val errorConfirmacionContrasena = findViewById<TextView>(R.id.tv_error_confirmacionContrasena)

        val loadingSpinner = findViewById<ProgressBar>(R.id.progressBar)
        loadingSpinner.visibility = View.GONE

        val btnSignup = findViewById<Button>(R.id.btn_signup)



        var dia:String=""
        var mes:String=""
        var anio:String=""
        var ciudad:Int=0

        spinnerDiaFechaNacimiento.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val diaP = parent?.getItemAtPosition(position)
                dia = diaP.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un día",Toast.LENGTH_SHORT).show()
                }
            }
        }

        spinnerMesFechaNacimiento.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val mesP = parent?.getItemAtPosition(position)
                if(mesP.toString() == "Enero")
                    mes =  "01"
                else if(mesP.toString() == "Febrero")
                    mes =  "02"
                else if(mesP.toString() == "Marzo")
                    mes =  "03"
                else if(mesP.toString() == "Abril")
                    mes =  "04"
                else if(mesP.toString() == "Mayo")
                    mes =  "05"
                else if(mesP.toString() == "Junio")
                    mes =  "06"
                else if(mesP.toString() == "Julio")
                    mes =  "07"
                else if(mesP.toString() == "Agosto")
                    mes =  "08"
                else if(mesP.toString() == "Septiembre")
                    mes =  "09"
                else if(mesP.toString() == "Octubre")
                    mes =  "10"
                else if(mesP.toString() == "Noviembre")
                    mes =  "11"
                else if(mesP.toString() == "Diciembre")
                    mes =  "12"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un mes",Toast.LENGTH_SHORT).show()
                }
            }
        }

        spinnerAnioFechaNacimiento.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val anioP = parent?.getItemAtPosition(position)
                anio = anioP.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un año", Toast.LENGTH_SHORT).show()
                }
            }

        }

        spinner_ciudad.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val ciudadP = parent?.getItemAtPosition(position)
                if(ciudadP.toString() == "Monterrey")
                    ciudad = 948
                else if(ciudadP.toString() == "Santa Catarina")
                    ciudad = 969
                else if(ciudadP.toString() == "San Pedro Garza García")
                    ciudad = 970
                else if(ciudadP.toString() == "San Nicolás de los Garza")
                    ciudad = 971
                else if(ciudadP.toString() == "Apodaca")
                    ciudad = 973
                else if(ciudadP.toString() == "Guadalupe")
                    ciudad = 983
                else if(ciudadP.toString() == "Escobedo")
                    ciudad = 968
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione una ciudad",Toast.LENGTH_SHORT).show()
                }
            }

        }



        btnSignup.setOnClickListener {
            var error=false
            loadingSpinner.visibility = View.VISIBLE
            var fechaNacT:String="";
            if(tbNombre.text.isNullOrEmpty())
            {
                error=true
                errorNombre.text = "campo requerido"
                errorNombre.visibility = TextView.VISIBLE
            }else if(errorNombre.visibility == TextView.VISIBLE)
                errorNombre.visibility = TextView.INVISIBLE

            if(tbApellidoPaterno.text.isNullOrEmpty())
            {
                error=true
                errorApPaterno.text = "campo requerido"
                errorApPaterno.visibility = TextView.VISIBLE
            }else if(errorApPaterno.visibility == TextView.VISIBLE)
                errorApPaterno.visibility = TextView.INVISIBLE

            if(tbApellidoMaterno.text.isNullOrEmpty())
            {
                error=true
                errorApMaterno.text = "campo requerido"
                errorApMaterno.visibility = TextView.VISIBLE
            }else if(errorApMaterno.visibility == TextView.VISIBLE)
                errorApMaterno.visibility = TextView.INVISIBLE


            if(!anio.toString().isNullOrEmpty() && !mes.toString().isNullOrEmpty() && !dia.toString().isNullOrEmpty())
            {
                fechaNacT = anio.toString()+"-"+mes.toString()+"-"+dia.toString()+"T00:00:00.000Z"
            }
            else
                error=true;

            if(tbDireccion.text.isNullOrEmpty())
            {
                error =true;
                errorDireccion.text="campo requerido"
                errorDireccion.visibility = TextView.VISIBLE
            }else if(errorDireccion.visibility == TextView.VISIBLE)
                errorDireccion.visibility=TextView.INVISIBLE

            if(tbCodigoPostal.text.isNullOrEmpty())
            {
                error=true
                errorCodigoPostal.text = "campo requerido"
                errorCodigoPostal.visibility = TextView.VISIBLE
            }else if(errorCodigoPostal.visibility == TextView.VISIBLE)
                errorCodigoPostal.visibility = TextView.INVISIBLE

            if(tbTelefono.text.isNullOrEmpty())
            {
                error=true
                errorTelefono.text = "campo requerido"
                errorTelefono.visibility = TextView.VISIBLE
            }else if(errorTelefono.visibility == TextView.VISIBLE)
                errorTelefono.visibility = TextView.INVISIBLE

            if(tbNombreUsuario.text.isNullOrEmpty())
            {
                error=true
                errorNombreUsuario.text = "campo requerido"
                errorNombreUsuario.visibility = TextView.VISIBLE
            }else {
                errorNombreUsuario.visibility = TextView.INVISIBLE

                //Validar que no exista el nombre de usuario
            }

            if(tbCorreoElectronico.text.isNullOrEmpty())
            {
                error=true
                errorCorreoElectronico.text = "campo requerido"
                errorCorreoElectronico.visibility = TextView.VISIBLE
            }else {
                errorCorreoElectronico.visibility = TextView.INVISIBLE
                val isEmailInFormat = Patterns.EMAIL_ADDRESS.matcher(tbCorreoElectronico.text).matches()
                if(!isEmailInFormat){
                    error=true
                    errorCorreoElectronico.text = "Correo no esta en formato correcto."
                    errorCorreoElectronico.visibility = TextView.VISIBLE
                }else{
                    errorCorreoElectronico.visibility = TextView.INVISIBLE
                }
            }

            if(tbContrasena.text.isNullOrBlank())
            {
                error=true
                errorContrasena.text = "campo requerido"
                errorContrasena.visibility = TextView.VISIBLE
            }else if(errorContrasena.visibility == TextView.VISIBLE){
                errorContrasena.visibility = TextView.INVISIBLE
                if( !checkPassWord(tbContrasena.text.toString())){
                    error=true
                    errorContrasena.text = "La contraseña debe incluir una Mayuscula, Minuscula, un numero y tener 8 characteres como minimo."
                    errorContrasena.visibility = TextView.VISIBLE
                }else{
                    errorContrasena.visibility = TextView.INVISIBLE
                }
            }


            if(tbConfirmacionContrasena.text.toString() != tbContrasena.text.toString())
            {
                error=true
                errorConfirmacionContrasena.text = "las contraseñas no coinciden."
                errorConfirmacionContrasena.visibility = TextView.VISIBLE
            }else{
                errorConfirmacionContrasena.visibility = TextView.INVISIBLE
            }


            if(error) {
                loadingSpinner.visibility = View.GONE
                errorGeneral.text="Existen errores en la captura. Por favor verifique su información. 😅"
            } else {
                errorGeneral.visibility = TextView.INVISIBLE

                imgPerfil.invalidate()
                var bitmap = imgPerfil.drawable.toBitmap()

                val encoder = ImageParser()
                val imageString = encoder.convert(bitmap)
                println(imageString)


                var client = OkHttpClient()
                var request = OkHttpRequest(client)

                var fechaNac = fechaNacT
                val usuarioNuevo = SignupModel(
                    tbNombre.text.toString(),
                    tbApellidoPaterno.text.toString(),
                    tbApellidoMaterno.text.toString(),
                    fechaNac.toString(),
                    tbDireccion.text.toString(),
                    tbNoExterior.text.toString(),
                    tbNoInterior.text.toString(),
                    tbCodigoPostal.text.toString(),
                    ciudad,
                    tbNombreUsuario.text.toString(),
                    tbCorreoElectronico.text.toString(),
                    tbTelefono.text.toString(),
                    tbContrasena.text.toString(),
                    imageString
                )

                Toast.makeText(applicationContext, "Contactando a la nube, espere un momento...", Toast.LENGTH_SHORT).show()

                val url ="https://patoparra.com/api/Security/CreateUser"

                request.signup(url,usuarioNuevo, object:Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("onFailure",e.toString())
                        runOnUiThread {
                            loadingSpinner.visibility = View.GONE
                            Toast.makeText(applicationContext,e.message, Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()?.string()
                        println(response)
                        if(responseData == "Usuario creado correctamente")
                        {
                            var loggeduser = Persona(0,tbNombreUsuario.text.toString(), tbNombre.text.toString(),tbApellidoPaterno.text.toString(), tbApellidoMaterno.text.toString(),fechaNac,tbCorreoElectronico.text.toString(),tbContrasena.text.toString(),tbTelefono.text.toString(),0)
                            val success =ldb.InsertLoggedUser(loggeduser, true)

                            runOnUiThread {
                                loadingSpinner.visibility = View.GONE
                                Toast.makeText(applicationContext,"Se ha creado su cuenta, Bienvenido", Toast.LENGTH_LONG).show()
                            }

                            val registerActivity = Intent(applicationContext, MainActivity::class.java)
                            startActivity(registerActivity)
                        }else
                        {
                            runOnUiThread {
                                Toast.makeText(applicationContext, responseData.toString(),Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                })
            }
        }

        btnImgPerfil.setOnClickListener{
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
        if(resultCode  == RESULT_OK && requestCode == 1){
            val imgbm = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()

            imgbm.compress(Bitmap.CompressFormat.JPEG,80,stream)

            profile_img.setImageBitmap(imgbm)
        }
    }





    private fun checkPassWord(str: String): Boolean {
        var ch: Char
        var capitalFlag = false
        var lowerCaseFlag = false
        var numberFlag = false
        if(str.length>=8){
            for (i in 0 until str.length) {
                ch = str[i]
                if (Character.isDigit(ch)) {
                    numberFlag = true
                } else if (Character.isUpperCase(ch)) {
                    capitalFlag = true
                } else if (Character.isLowerCase(ch)) {
                    lowerCaseFlag = true
                }
                if (numberFlag && capitalFlag && lowerCaseFlag) return true
            }
        }
        return false
    }



}