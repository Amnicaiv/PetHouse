package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.Models.loginModel

import kotlinx.android.synthetic.main.layout_iniciosesion.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_iniciosesion);

        actionBar?.hide()
        supportActionBar?.hide()

        //Log in
        val buttonLogin = findViewById<Button>(R.id.bLogin)
        val etUsername = findViewById<EditText>(R.id.txt_username)
        val etPassword = findViewById<EditText>(R.id.txt_password)
        val errorUsername =  findViewById<TextView>(R.id.tv_error_username)
        val errorPassword = findViewById<TextView>(R.id.tv_error_password)

        //Signup
        val lnkSignup = findViewById<TextView>(R.id.lnk_signup)
        lnkSignup.setOnClickListener {
            val registerActivity = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(registerActivity)
        }


        buttonLogin.setOnClickListener(){

            if(etUsername.text.isEmpty() || etPassword.text.isEmpty())
            {
                if(etUsername.text.isEmpty())
                {
                    errorUsername.text = "campo requerido"
                    errorUsername.visibility = View.VISIBLE
                }
                else
                    errorUsername.visibility = View.INVISIBLE
                if(etPassword.text.isEmpty())
                {
                    errorPassword.text = "campo requerido"
                    errorPassword.visibility = View.VISIBLE
                }
                else
                    errorPassword.visibility=View.INVISIBLE
            }
            else
            {
                if(!etUsername.text.isNullOrEmpty())
                {
                    errorUsername.text = ""
                    errorUsername.visibility = View.INVISIBLE
                }
                if(!etPassword.text.isNullOrEmpty())
                {
                    errorPassword.text = ""
                    errorPassword.visibility = View.INVISIBLE
                }

                var client = OkHttpClient()
                var request = OkHttpRequest(client)

                val credentials = loginModel(etUsername.text.toString(), etPassword.text.toString())
                val url = "https://localhost:44337/api/security/login"

                //Send POST Request to server
                request.login(url,credentials, object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(applicationContext,"Error al conectar con el servidor. Intente mas tarde.", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()?.string()
                        runOnUiThread {
                            try{
                                var token = responseData.toString()
                                println("Request Succesful")
                                Log.d("token",token)

                                when(token){
                                    "Usuario no existe"->{
                                        Log.d("response1","usuario no existe")
                                    }
                                    "Credenciales inválidas"->{
                                        Log.d("response2","contraseña invalida")
                                    }
                                    else->{
                                        //Save token on Shared Preferences
                                        val sharedPref = this@MainActivity.getSharedPreferences("key",Context.MODE_PRIVATE) ?: return@runOnUiThread
                                        with(sharedPref.edit()){
                                            putString("token",token)
                                            commit()
                                        }
                                        val registerActivity = Intent(applicationContext, DashboardActivity::class.java)
                                        startActivity(registerActivity)
                                    }
                                }


                            }catch(e:JSONException){
                                e.printStackTrace()
                            }
                        }
                    }

                })
            }




        }
        //------------------------------------------------------











    }


}
