package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.GetUserDataModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_dashboard.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class DashboardActivity: AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)

        val userToken = this.getSharedPreferences("key",0)
        val tokenString = userToken.getString("token","No token found")

        val msjBienvenida = findViewById<TextView>(R.id.tv_mensaje_bienvenida)

        msjBienvenida.setText("Bienvenido, " + prefs.getString("name","Unknkown"))


       // val ldb = LocalDatabaseManager(this)
        val nick = userToken.getString("apodo","No nickname found")

        val url = "https://patoparra.com/api/cliente/getfromusername?username=$nick"


        var client = OkHttpClient()
        var request = OkHttpRequest(client)

        //Send POST Request to server
        request.getUserData(url, object: Callback {
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

                        val json = JSONObject(responseData)
                        val userid = json.getString("id")

                        val sharedPref = this@DashboardActivity.getSharedPreferences("key",Context.MODE_PRIVATE) ?: return@runOnUiThread
                        with(sharedPref.edit()){
                            putString("token",tokenString)
                            putString("apodo",nick)
                            putString("id",userid)
                            commit()
                        }


                    }catch(e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

        })






        val sharedPref = applicationContext.getSharedPreferences("token", Context.MODE_PRIVATE)?: return

        val token = sharedPref.getString("token","")
        Log.d("tokenBearer",token.toString())


        val ivPets = findViewById<ImageView>(R.id.iv_pets)
        ivPets.setOnClickListener{
            val registerActivity = Intent(applicationContext, MisMascotasActivity::class.java)
            startActivity(registerActivity)
        }

        val ivHoteles = findViewById<ImageView>(R.id.iv_hotel)
        ivHoteles.setOnClickListener{
            val estancias = Intent(applicationContext, MisEstancias::class.java)
            startActivity(estancias)
        }

        val ivReservaciones = findViewById<ImageView>(R.id.imageView14)
        ivReservaciones.setOnClickListener(){
            val check = Intent(applicationContext, MisReservacionesActivity::class.java)
            startActivity(check)
        }

        actionBar?.hide()
        supportActionBar?.hide()




        this.navView.setNavigationItemSelectedListener { item ->

            when(item.itemId){
                R.id.op1->{
                    Log.i("OP1","Opcion 1")
                    val userInfoActivity = Intent(applicationContext, InformacionUsuario::class.java)
                    startActivity(userInfoActivity)
                }
                R.id.op2->{
                    Log.i("OP2","Opcion 2")
                    val mismascActivity = Intent(applicationContext, MisMascotasActivity::class.java)
                    startActivity(mismascActivity)
                }
                R.id.op3->{
                    Log.i("OP3","Opcion 3")
                    val reservationActivity = Intent(applicationContext, MisReservacionesActivity::class.java)
                    startActivity(reservationActivity)
                }
                R.id.op4->{
                    Log.i("OP4","Opcion 4")
                    val estanciasActivity = Intent(applicationContext, MisEstancias::class.java)
                    startActivity(estanciasActivity)
                }
            }
            false
        }

    }

    override fun onClick(v: View?) {
        Log.e("DA-btnSearch","Search Button pressed.");
        when(v!!.id)
        {
          /*  R.id.btnSearch ->{
                Log.e("SRActivity","Search Results Activity Called");
                this.onSearch();
            }*/
        }
    }

    private fun onSearch()
    {
        Log.e("DA-onSearch_f","Inside onSearch function");
        val searchUpActivityIntent: Intent = Intent(this,SearchResultsActivity::class.java);
        //searchUpActivityIntent.putExtra("data",this.tbEmailLogin.toString());
        startActivity(searchUpActivityIntent);
    }

}