package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class SearchResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_misreservaciones);

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
        val idString = prefs.getString("id","")
        val url = "https://patoparra.com/api/Hogar/Get/?userId=$idString"



        var client = OkHttpClient()
        var request = OkHttpRequest(client)

        request.getHogares(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                TODO("Not yet implemented")
            }


        })





    }
}