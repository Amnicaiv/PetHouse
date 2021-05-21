package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MisReservacionesAdapter(private val context: Context,
                              private val dataSource: ArrayList<ReservacionItem>
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }


    override fun getItem(position: Int): Any {
        return dataSource[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_misreservaciones, parent, false)

        // Get title element
        val titleTextView = rowView.findViewById(R.id.id_item_MRNombre) as TextView

        // Get subtitle element
        val subIngresoTextView = rowView.findViewById(R.id.id_item_MRfechaingreso) as TextView

        val subSalidaTextView = rowView.findViewById(R.id.id_item_MRfechasalida) as TextView


        // Get thumbnail element
        val thumbnailImageView = rowView.findViewById(R.id.id_item_MRImg) as ImageView

        // 1
        val reservacion = getItem(position) as ReservacionItem


        titleTextView.text = reservacion.nombre
        subIngresoTextView.text = reservacion.fechaEntrada
        subSalidaTextView.text = reservacion.fechaSalida

        thumbnailImageView.setImageResource(R.drawable.ic_baseline_person_24)

        /*   Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)*/



        return rowView
    }
}