package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_iniciosesion.*
import kotlinx.android.synthetic.main.layout_misestancias.*

class MisEstancias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_misestancias);

        this.button9.setOnClickListener(){
            Toast.makeText(this, "Validar informacion de usuario.", Toast.LENGTH_SHORT).show()
            val menuActivity = Intent(applicationContext, DashboardActivity::class.java)
            startActivity(menuActivity)
        }
    }

}