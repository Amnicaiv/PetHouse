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
import android.widget.*
import com.example.myapplication.Models.UserModel
import com.example.myapplication.Models.loginModel
import com.google.gson.Gson

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

        val loadingSpinnerLogin = findViewById<ProgressBar>(R.id.progressBar_login)
        loadingSpinnerLogin.visibility=View.GONE

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)

        val ldb = LocalDatabaseManager(this)
        var loginPerson :Persona;
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


        buttonLogin.setOnClickListener {
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

                loadingSpinnerLogin.visibility=View.VISIBLE
                buttonLogin.isEnabled = false;

                var client = OkHttpClient()
                var request = OkHttpRequest(client)

                Toast.makeText(applicationContext, "Verificando su informacion, espere un momento...", Toast.LENGTH_SHORT).show()

                val credentials = loginModel(etUsername.text.toString(), etPassword.text.toString())
                val url = "https://patoparra.com/api/security/login"

                //Send POST Request to server
                request.login(url,credentials, object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            loadingSpinnerLogin.visibility=View.GONE
                            Toast.makeText(applicationContext,"Error al conectar con el servidor. Intente mas tarde.", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()?.string()
                        runOnUiThread {
                            loadingSpinnerLogin.visibility=View.GONE
                            try{
                                var token = responseData.toString()
                                println("Request Succesful")
                                Log.d("token",token)

                                when(token){
                                    "Usuario no existe"->{
                                        runOnUiThread {
                                            buttonLogin.isEnabled = false;
                                            Toast.makeText(applicationContext,"Usuario no existe",Toast.LENGTH_SHORT).show()
                                            val registerActivity = Intent(applicationContext, SignUpActivity::class.java)
                                            startActivity(registerActivity)
                                        }
                                    }
                                    "Credenciales inválidas"->{
                                        runOnUiThread {
                                            buttonLogin.isEnabled = false;
                                            Toast.makeText(applicationContext,"Credenciales incorrectas, verifique su información", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                    else->{
                                        //Send request to get id
                                        val urlGetUserByUsername="https://patoparra.com/api/cliente/getfromusername?username=" + credentials.username.toString()
                                        request.getUser(urlGetUserByUsername,object:Callback{
                                            override fun onFailure(call: Call, e: IOException) {
                                                runOnUiThread {
                                                    Toast.makeText(applicationContext, "No se pudo obtener información del usuario. verifique su conexión",Toast.LENGTH_LONG).show()
                                                }
                                            }

                                            override fun onResponse(call: Call,response: Response) {
                                                val responseData =  response.body()?.string()
                                                val user = Gson().fromJson<UserModel>(responseData, UserModel::class.java)
                                                println(token)
                                                println(user.id)
                                                println(user.name)
                                                //Actualizar prefs
                                                prefs.edit().putString("access_token",token).commit()
                                                prefs.edit().putString("id",user.id).commit()
                                                prefs.edit().putString("name",user.name).commit()
                                                prefs.edit().putString("nick",etUsername.text.toString()).commit()

                                                //Guardar en Internal DB
                                                if(ldb.GetLoggedUser().isNotEmpty()){
                                                    val persona = ldb.GetLoggedUser()
                                                    //ldb.deletePerson(persona[0].id)
                                                    val newuser = Persona(0,"", "","","","","", etPassword.text.toString(),"",0)
                                                    //ldb.InsertLoggedUser()
                                                }

                                                runOnUiThread {
                                                    Toast.makeText(applicationContext,"Bienvenido", Toast.LENGTH_LONG).show()
                                                }
                                                val registerActivity = Intent(applicationContext, DashboardActivity::class.java)
                                                startActivity(registerActivity)
                                            }

                                        })
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








    }


}
