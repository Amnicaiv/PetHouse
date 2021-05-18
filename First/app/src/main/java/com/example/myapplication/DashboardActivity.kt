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

        this.btnSearch.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        Log.e("DA-btnSearch","Search Button pressed.");
        when(v!!.id)
        {
            R.id.btnSearch ->{
                Log.e("SRActivity","Search Results Activity Called");
                this.onSearch();
            }
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