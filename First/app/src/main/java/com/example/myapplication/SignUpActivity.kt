package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.SignupModel
import kotlinx.android.synthetic.main.layout_signup.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
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
        val tbFechaNacimiento = findViewById<TextView>(R.id.txt_fechaNacimiento)
        val tbDireccion =  findViewById<TextView>(R.id.txt_direccion)
        val tbNoExterior = findViewById<TextView>(R.id.txt_noExt)
        val tbNoInterior = findViewById<TextView>(R.id.txt_noInt)
        val tbCodigoPostal = findViewById<TextView>(R.id.txt_codigoPostal)
        val tbCiudad = findViewById<TextView>(R.id.txt_ciudad)
        val tbTelefono = findViewById<TextView>(R.id.txt_telefono)
        val tbNombreUsuario = findViewById<TextView>(R.id.txt_nombreUsuario)
        val tbCorreoElectronico = findViewById<TextView>(R.id.txt_correoElectronico)
        val tbContrasena = findViewById<TextView>(R.id.txt_contrasena)
        val tbConfirmacionContrasena = findViewById<TextView>(R.id.txt_confirmacionContrasena)

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

        val btnSignup = findViewById<Button>(R.id.btn_signup)



        //tbFechaNacimiento.addTextChangedListener(textWatcher)





        btnSignup.setOnClickListener {
            var error=false



            var datetext = this.txt_fechaNacimiento.text.toString()
            datetext += "T18:12:43.724Z"



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

            if(tbFechaNacimiento.text.isNullOrEmpty())
            {
                error=true
                errorFechaNacimiento.text = "campo requerido"
                errorFechaNacimiento.visibility = TextView.VISIBLE
            }else{
                errorFechaNacimiento.visibility = TextView.INVISIBLE
                /* val formatter:SimpleDateFormat = SimpleDateFormat("dd/MM/yyy")
                 val fechaNacimiento = formatter.parse(tbFechaNacimiento.text.toString())
                 println(fechaNacimiento)*/
            }

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

            if(tbCiudad.text.isNullOrEmpty())
            {
                error=true
                errorCiudad.text = "campo requerido"
                errorCiudad.visibility = TextView.VISIBLE
            }else if(errorCiudad.visibility == TextView.VISIBLE)
                errorCiudad.visibility = TextView.INVISIBLE

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

            Log.d("error",error.toString());
            if(error){
                errorGeneral.text="Existen errores en la captura. Por favor verifique su información. 😅"
            }else{
                errorGeneral.visibility = TextView.INVISIBLE
                var client = OkHttpClient()
                var request = OkHttpRequest(client)

                var fechaNac = "1980-05-26T18:12:43.724Z"//tbFechaNacimiento.text.toString().replace('/','-') + "T18:12:43.724Z"
                val usuarioNuevo = SignupModel(
                    tbNombre.text.toString(),
                    tbApellidoPaterno.text.toString(),
                    tbApellidoMaterno.text.toString(),
                    fechaNac.toString(),
                    tbDireccion.text.toString(),
                    tbNoExterior.text.toString(),
                    tbNoInterior.text.toString(),
                    tbCodigoPostal.text.toString(),
                    3,
                    tbNombreUsuario.text.toString(),
                    tbCorreoElectronico.text.toString(),
                    tbTelefono.text.toString(),
                    tbContrasena.text.toString()
                )

                Toast.makeText(applicationContext, "Contactando a la nube, espere un momento...", Toast.LENGTH_SHORT).show()

                val url ="https://patoparra.com/api/Security/CreateUser"

                request.signup(url,usuarioNuevo, object:Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("onFailure",e.toString())
                        runOnUiThread {
                            Toast.makeText(applicationContext,"Hubo un error al intentar crear su cuenta. Intente mas tarde.", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()?.string()
                        Log.d("onSuccess",responseData.toString())
                        if(responseData == "Usuario creado correctamente")
                        {


                            var loggeduser = Persona(0,tbNombreUsuario.text.toString(), tbNombre.text.toString(),tbApellidoPaterno.text.toString(), tbApellidoMaterno.text.toString(),tbFechaNacimiento.text.toString(),tbCorreoElectronico.text.toString(),tbContrasena.text.toString(),tbTelefono.text.toString(),0)
                            val success =ldb.InsertLoggedUser(loggeduser, true)

                            runOnUiThread {
                                Toast.makeText(applicationContext,"Se ha creado su cuenta, Bienvenido", Toast.LENGTH_LONG).show()
                            }

                            val registerActivity = Intent(applicationContext, DashboardActivity::class.java)
                            startActivity(registerActivity)
                        }
                    }
                })
            }
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