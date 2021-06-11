package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.GetUserDataModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_dashboard.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class DashboardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);

        val btnBuscarEstancias = findViewById<Button>(R.id.btn_buscar_estancia)
        val btnMisMascotas = findViewById<Button>(R.id.btn_mascotas)
        val btnMisEstancias = findViewById<Button>(R.id.btn_estancias)
        val btnMisReservaciones = findViewById<Button>(R.id.btn_reservaciones)
        val btnMiPerfil = findViewById<ImageView>(R.id.iv_mi_perfil)
        val btnCerrarSesion = findViewById<Button>(R.id.btn_salir)

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)

        val msjBienvenida = findViewById<TextView>(R.id.tv_mensaje_bienvenida)

        msjBienvenida.setText("Bienvenido, " + prefs.getString("name","Unknkown"))

       // val ldb = LocalDatabaseManager(this)

        btnMisMascotas.setOnClickListener{
            val registerActivity = Intent(applicationContext, MisMascotasActivity::class.java)
            startActivity(registerActivity)
        }


        btnMisEstancias.setOnClickListener{
            val estancias = Intent(applicationContext, MisEstancias::class.java)
            startActivity(estancias)
        }


        btnMisReservaciones.setOnClickListener(){
            val check = Intent(applicationContext, MisReservacionesActivity::class.java)
            startActivity(check)
        }

        btnMiPerfil.setOnClickListener(){
            val userInfoActivity = Intent(applicationContext, InformacionUsuario::class.java)
            startActivity(userInfoActivity)
        }

        btnBuscarEstancias.setOnClickListener(){
            val hogaresDisponibles = Intent(applicationContext, HogaresDisponiblesActivity::class.java)
            startActivity(hogaresDisponibles)
        }

        btnCerrarSesion.setOnClickListener {
            prefs.edit().clear().commit()
            this.finish()
        }
    }
}