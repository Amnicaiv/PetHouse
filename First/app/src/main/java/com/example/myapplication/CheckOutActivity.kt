package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.ReservacionModel
import kotlinx.android.synthetic.main.layout_reservacion_checkout.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class CheckOutActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reservacion_checkout);

        this.button16.setOnClickListener(){
            val userToken = this.getSharedPreferences("key",0)
            val idString = userToken.getString("id","No id found")

            var client = OkHttpClient()
            var request = OkHttpRequest(client)

            var url="https://patoparra.com/api/Reservacion/Create"


            val reservacionNueva = ReservacionModel(idString,1,1,"2021-06-03T16:30:18.026Z","2021-06-09T16:30:18.026Z",600,true,"pedigree","esta es una descripcion")

            request.setReservation(url,reservacionNueva,object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Failure to save pet")
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body()?.string()
                    Log.d("reqSuccess",responseData.toString())

                }

            })

        }

    }
}