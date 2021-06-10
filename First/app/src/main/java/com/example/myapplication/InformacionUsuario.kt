package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_info_usuario.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class InformacionUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_info_usuario);


        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)

        /*val userToken = this.getSharedPreferences("key",0)
        val tokenString = userToken.getString("token","No token found")
        val nick = userToken.getString("apodo","No nickname found")*/

        val idString = prefs.getString("id","Unkown")
        val nick = prefs.getString("nick","Unkown")

        val url = "https://patoparra.com/api/cliente/getfromusername?username=$nick"

        Toast.makeText(applicationContext, nick.toString(), Toast.LENGTH_SHORT).show()

        var client = OkHttpClient()
        var request = OkHttpRequest(client)

        val emailTV =this.tbEmailSignup
        val nameTV = this.tbPasswordSignup
        val nickTV = this.tbConfirmedPassword

        //Send POST Request to server
        request.getUserData(url, object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    //Toast.makeText(applicationContext,"Error al conectar con el servidor. Intente mas tarde.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                runOnUiThread {
                    try{
                        var token = responseData.toString()
                        println("Request Succesful")
                        Log.d("token",token)

                        val json = JSONObject(responseData)

                        val username = json.getString("name")
                        val email = json.getString("email")

                        emailTV.text = email
                        nameTV.text = username
                        nickTV.text= nick


                    }catch(e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

        })

        this.btnCancel.setOnClickListener(){
            finish()
        }
    }
}