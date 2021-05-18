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
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_camera_layout.*
import kotlinx.android.synthetic.main.layout_login.*
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);


        /*this.imgbtn_takePhoto.setOnClickListener(this);*/
/*        this.btnOpenNewWindow.setOnClickListener(this);
        this.btnPhoneCall.setOnClickListener(this);
        this.btnOpenMaps.setOnClickListener(this);
        this.btnTakePhoto.setOnClickListener(this);
        this.btnSendMail.setOnClickListener(this);*/


        /*Log.e("Activity",this.toString());
        this.txtEmail.setText("patricio.escuderoprr@uanl.edu.mx");
        this.txtName.setText("Patricio Escudero");
        this.btnAdd.setText("Registrar");

        //Atraves de FindById
        val textEmailVal: EditText = findViewById<EditText>(R.id.txtEmail);
        textEmailVal.setText("patricio.escuderoprr@uanl.edu.mx");
        val textNombreVal: EditText = findViewById<EditText>(R.id.txtName);
        textNombreVal.setText("Eduardo Parra");
        val btnAddObj: Button = findViewById<Button>(R.id.btnAdd);
        btnAdd.setText("Iniciar Sesión");

        this.btnAdd.setOnClickListener(this);
        this.btnAdd.setOnLongClickListener(this);
        this.txtTitle.setOnClickListener(this);

        val persona = Persona("Patricio",28);
        Log.e("Persona",persona.toString());*/
    }


}
