package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.FullPetModel
import kotlinx.android.synthetic.main.layout_createpet.*
import kotlinx.android.synthetic.main.layout_petinfo.*

class PetInfiActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_petinfo)

        val petName = intent.getStringExtra("PET_NAME")

        val ldb = LocalDatabaseManager(applicationContext)



        val arrayList = petName?.let { ldb.GetPetInfo(it) }

        val petSelected = arrayList?.get(0)

        /*this.editTextTextPersonName6.setText(petSelected.)
        this.editTextTextPersonName7.setText(arrayList?.get(0)?.edad).toString()*/

        if (petSelected != null) {
            val encoder = ImageParser()
            this.editTextTextPersonName6.setText(petSelected.nombre)
            this.editTextTextPersonName7.setText(petSelected.edad.toString())
            this.imageView7.setImageBitmap(petSelected.imagen?.let { encoder.convert(it) })
            this.imageView10.setImageBitmap(petSelected.cartillaPdf?.let { encoder.convert(it) })
            this.imageView12.setImageBitmap(petSelected.alimento?.let{encoder.convert(it) })


        }


        var mLastClickTime = 0.0

        var doublePressPrev = false

        this.button9.setOnClickListener(){
            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble();

            if(!doublePressPrev){
                finish()
            }
        }



        this.button11.setOnClickListener(){
            doublePressPrev = SystemClock.elapsedRealtime() - mLastClickTime < 1000
            mLastClickTime = SystemClock.elapsedRealtime().toDouble()

            if(!doublePressPrev){
                finish()
            }
        }


    }

}