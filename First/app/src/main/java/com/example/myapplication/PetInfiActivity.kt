package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_petinfo.*

class PetInfiActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_petinfo)

        val petName = intent.getStringExtra("nombre")
        val petType = intent.getStringExtra("raza")

        this.editTextTextPersonName6.setText(petName)



    }

}