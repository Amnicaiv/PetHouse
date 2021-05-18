package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_signup.*

class SignUpActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        this.btnSignup.setOnClickListener(){
            Toast.makeText(this, "Validar creacion de usuario.", Toast.LENGTH_SHORT).show()
            val menuActivity = Intent(applicationContext, DashboardActivity::class.java)
            startActivity(menuActivity)
        }
    }


}