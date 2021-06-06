package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
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

        var diaE:String =""
        var mesE:String =""
        var anioE:String=""

        var diaS:String=""
        var mesS:String=""
        var anioS:String=""


        val houseId = intent.getStringExtra("HOUSE_ID").toString().toInt()

        val spinnerDiaEntrada = this.spinner_dia
        val spinnerDiaSalida = this.spinner_dia2

        val spinnerMesEntrada = this.spinner_mes
        val spinnerMesSalida = this.spinner_mes2

        val spinnerAnioEntrada = this.spinner_anio
        val spinnerAnioSalida = this.spinner_anio2




        spinnerDiaEntrada.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val diaP = parent?.getItemAtPosition(position)
                diaE = diaP.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un día", Toast.LENGTH_SHORT).show()
                }
            }
        }

        spinnerMesEntrada.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val mesP = parent?.getItemAtPosition(position)
                if(mesP.toString() == "Enero")
                    mesE =  "01"
                else if(mesP.toString() == "Febrero")
                    mesE =  "02"
                else if(mesP.toString() == "Marzo")
                    mesE =  "03"
                else if(mesP.toString() == "Abril")
                    mesE =  "04"
                else if(mesP.toString() == "Mayo")
                    mesE =  "05"
                else if(mesP.toString() == "Junio")
                    mesE =  "06"
                else if(mesP.toString() == "Julio")
                    mesE =  "07"
                else if(mesP.toString() == "Agosto")
                    mesE =  "08"
                else if(mesP.toString() == "Septiembre")
                    mesE =  "09"
                else if(mesP.toString() == "Octubre")
                    mesE =  "10"
                else if(mesP.toString() == "Noviembre")
                    mesE =  "11"
                else if(mesP.toString() == "Diciembre")
                    mesE =  "12"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un mes", Toast.LENGTH_SHORT).show()
                }
            }
        }

        spinnerAnioEntrada.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val anioP = parent?.getItemAtPosition(position)
                anioE = anioP.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un año", Toast.LENGTH_SHORT).show()
                }
            }

        }



        spinnerDiaSalida.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val diaP = parent?.getItemAtPosition(position)
                diaS = diaP.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un día", Toast.LENGTH_SHORT).show()
                }
            }
        }

        spinnerMesSalida.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val mesP = parent?.getItemAtPosition(position)
                if(mesP.toString() == "Enero")
                    mesS =  "01"
                else if(mesP.toString() == "Febrero")
                    mesS =  "02"
                else if(mesP.toString() == "Marzo")
                    mesS =  "03"
                else if(mesP.toString() == "Abril")
                    mesS =  "04"
                else if(mesP.toString() == "Mayo")
                    mesS =  "05"
                else if(mesP.toString() == "Junio")
                    mesS =  "06"
                else if(mesP.toString() == "Julio")
                    mesS =  "07"
                else if(mesP.toString() == "Agosto")
                    mesS =  "08"
                else if(mesP.toString() == "Septiembre")
                    mesS =  "09"
                else if(mesP.toString() == "Octubre")
                    mesS =  "10"
                else if(mesP.toString() == "Noviembre")
                    mesS =  "11"
                else if(mesP.toString() == "Diciembre")
                    mesS =  "12"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un mes", Toast.LENGTH_SHORT).show()
                }
            }
        }

        spinnerAnioSalida.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val anioP = parent?.getItemAtPosition(position)
                anioS = anioP.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Seleccione un año", Toast.LENGTH_SHORT).show()
                }
            }

        }


        val userToken = this.getSharedPreferences("key",0)
        val idString = userToken.getString("id","No id found")

        var client = OkHttpClient()
        var request = OkHttpRequest(client)

        var url="https://patoparra.com/api/Reservacion/Create"



        this.button16.setOnClickListener(){



            val fechaEntrada = anioE.toString()+"-"+mesE.toString()+"-"+diaE.toString()+"T00:00:00.000Z"

            val fechaSalida = anioS.toString()+"-"+mesS.toString()+"-"+diaS.toString()+"T00:00:00.000Z"

            val montoPagar = this.editTextNumberSigned.text.toString().toInt()
            val traeAlim = this.checkBox2.isChecked

            val descrpAlim = this.editTextTextPersonName4.text.toString()

            val especAlim = this.editTextTextPersonName.text.toString()


            val reservacionNueva = ReservacionModel(idString,houseId,1, fechaEntrada, fechaSalida, montoPagar, traeAlim,descrpAlim,especAlim)

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