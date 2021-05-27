package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_signup.*
import java.util.*

class SignUpActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

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
        val tbContrasena = findViewById<TextView>(R.id.txt_password)
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
        val errorContrasena = findViewById<TextView>(R.id.tv_error_contrasena)
        val errorConfirmacionContrasena = findViewById<TextView>(R.id.tv_error_confirmacionContrasena)

        val btnSignup = findViewById<Button>(R.id.btn_signup)

        btnSignup.setOnClickListener {
            var error=false
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



            if(error){
                errorGeneral.text="Existen errores en la captura. Por favor verifique su información. 😅"
            }


        }
    }


}