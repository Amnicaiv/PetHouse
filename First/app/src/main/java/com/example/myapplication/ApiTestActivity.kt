package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_prueba_comunicacion_webapi.*
import org.json.JSONObject


class ApiTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_prueba_comunicacion_webapi);

        val apiCommunicator = ComunicacionApi()

        this.btn_conectarApi.setOnClickListener(){
            apiCommunicator.ConseguirUsuarioPorID(this,1)

        }


        this.btn_conectarApi2.setOnClickListener(){
            val textView = this.tv_prueba_api

            val queue = Volley.newRequestQueue(this)
            val id = 1
            val url = "https://www.httpvshttps.com/"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    textView.text = "Response is: ${response.substring(0, 500)}"
                },
                Response.ErrorListener { textView.text = "That didn't work!" })

            queue.add(stringRequest)
        }

    /*    fun conectGoogle(){
            val textView = this.tv_prueba_api

            val queue = Volley.newRequestQueue(this)
            val id = 1
            val url = "https://www.google.com"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    textView.text = "Response is: ${response.substring(0, 500)}"
                },
                Response.ErrorListener { textView.text = "That didn't work!" })

            queue.add(stringRequest)
        }

        fun conectApi(){
            val textView = this.tv_prueba_api

            val queue = Volley.newRequestQueue(this)
            val id = 1
            val url = "http://patoparra.com/api/cliente/getfromid/1"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    textView.text = "Response is: ${response.substring(0, 500)}"
                },
                Response.ErrorListener { textView.text = "That didn't work!" })

            queue.add(stringRequest)
        }*/
    }


}