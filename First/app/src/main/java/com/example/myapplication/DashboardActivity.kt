package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_dashboard.*
import kotlinx.android.synthetic.main.layout_login.*

class DashboardActivity: AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);


 /*       this.btnSearch.setOnClickListener(this);*/

        actionBar?.hide()
        supportActionBar?.hide()

/*        this.btnSearch.setOnClickListener(this);*/



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