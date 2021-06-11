package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Models.ReservacionModel
import kotlinx.android.synthetic.main.layout_reservacion_checkout.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class CheckOutActivity :AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reservacion_checkout);

        val nombrePropietario = intent.getStringExtra("OWNER_NAME")
        val houseId = intent.getStringExtra("HOUSE_ID").toString().toInt()
        val priceByNight = intent?.getDoubleExtra("PRICE",0.00)

        val tbNombrePropietario = findViewById<TextView>(R.id.tb_nombre_propietario)
        val tbCostoPorNoche = findViewById<TextView>(R.id.tb_preview_costo_por_noche)
        val cbDejarComida = findViewById<CheckBox>(R.id.cb_dejar_comida)
        val tbComentariosComida = findViewById<TextView>(R.id.tb_descripcion_comida)
        val tbComentarios = findViewById<TextView>(R.id.tb_comentarios)
        val tbMontoTotal = findViewById<TextView>(R.id.tb_monto_final)


        val spinnerDiaEntrada = this.spinner_dia
        val spinnerDiaSalida = this.spinner_dia2

        val spinnerMesEntrada = this.spinner_mes
        val spinnerMesSalida = this.spinner_mes2

        val spinnerAnioEntrada = this.spinner_anio
        val spinnerAnioSalida = this.spinner_anio2




        var montoAPagar=0.00

        tbNombrePropietario.setText(nombrePropietario)
        tbCostoPorNoche.setText("$ $priceByNight MXN")


        tbMontoTotal.isEnabled=false
        tbMontoTotal.setText("$ $priceByNight MXN")


        tbComentariosComida.isEnabled=false

        cbDejarComida.setOnCheckedChangeListener { _, isChecked ->
            tbComentariosComida.isEnabled = isChecked == true

            if(!isChecked && !tbComentariosComida.text.isNullOrEmpty())
                tbComentariosComida.setText("")
        }

        val fechaActual = LocalDateTime.now(Clock.systemDefaultZone())
        spinnerDiaEntrada.setSelection(fechaActual.dayOfMonth.dec())
        spinnerMesEntrada.setSelection(fechaActual.monthValue.dec())

        spinnerDiaSalida.setSelection(fechaActual.dayOfMonth)
        spinnerMesSalida.setSelection(fechaActual.monthValue.dec())

        var diaE:String =fechaActual.dayOfMonth.dec().toString()
        var mesE:String =fechaActual.monthValue.dec().toString()
        var anioE:String=fechaActual.year.toString()

        var diaS:String=fechaActual.dayOfMonth.toString()
        var mesS:String=fechaActual.monthValue.dec().toString()
        var anioS:String=fechaActual.year.toString()



        spinnerDiaEntrada.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val diaP = parent?.getItemAtPosition(position)
                diaE = diaP.toString()


                val format = SimpleDateFormat("dd-MM-yyyy",Locale.getDefault())
                val fechaInicial = format.parse("$diaE-$mesE-$anioE")
                val fechaFinal = format.parse("$diaS-$mesS-$anioS")
                val monto = calcularMontoTotal(priceByNight!!,fechaInicial,fechaFinal)
                Log.d("monto a pagar",monto.toString())
                tb_monto_final.setText(monto.toString())

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

        val prefs = getSharedPreferences("MySharedPrefs", MODE_PRIVATE)
        val idString = prefs.getString("id","No id found")

        var client = OkHttpClient()
        var request = OkHttpRequest(client)

        var url="https://patoparra.com/api/Reservacion/Create"



        this.button16.setOnClickListener(){



            val fechaEntrada = anioE.toString()+"-"+mesE.toString()+"-"+diaE.toString()+"T00:00:00.000Z"

            val fechaSalida = anioS.toString()+"-"+mesS.toString()+"-"+diaS.toString()+"T00:00:00.000Z"

            val montoPagar = this.tb_monto_final.text.toString().toInt()
            val traeAlim = this.cb_dejar_comida.isChecked

            val descrpAlim = this.tb_descripcion_comida.text.toString()

            val especAlim = this.tb_comentarios.text.toString()


            val reservacionNueva = ReservacionModel(idString,houseId,1, fechaEntrada, fechaSalida, montoPagar, traeAlim,descrpAlim,especAlim)

            request.setReservation(url,reservacionNueva,object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body()?.string()
                    runOnUiThread {
                        Toast.makeText(applicationContext, responseData, Toast.LENGTH_LONG).show()
                    }
                    Log.d("reqSuccess",responseData.toString())
                    finish()

                }

            })

        }



    }

    private fun calcularMontoTotal(costoPorNocheP:Double, fechaEntrada:Date, fechaSalida:Date):Double{

        return ((((fechaSalida.time - fechaEntrada.time) / (1000*60*60*24))+1) * costoPorNocheP)


    }
}