package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_camera_layout.*
import kotlinx.android.synthetic.main.layout_iniciosesion.*
import kotlinx.android.synthetic.main.layout_login.*
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_iniciosesion);

        this.button3.setOnClickListener(){
            Toast.makeText(this, "Validar informacion de usuario.", Toast.LENGTH_SHORT).show()
            val menuActivity = Intent(applicationContext, MisMascotasActivity::class.java)
            startActivity(menuActivity)
        }

        this.button15.setOnClickListener(){
            Toast.makeText(this, "Enviar a pantalla de registro", Toast.LENGTH_SHORT).show()
            val registerActivity = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(registerActivity)
        }


    }


}
