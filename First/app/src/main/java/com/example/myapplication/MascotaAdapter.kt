package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MascotaAdapter(private val context: Context,
                     private val dataSource: ArrayList<MascotaItem>) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.list_item_pet, parent, false)

        // Get title element
        val titleTextView = rowView.findViewById(R.id.id_item_PetNombre) as TextView

        // Get subtitle element
        val subtitleTextView = rowView.findViewById(R.id.id_item_PetRaza) as TextView


        // Get thumbnail element
        val thumbnailImageView = rowView.findViewById(R.id.id_item_PetImg) as ImageView

        // 1
        val pet = getItem(position) as MascotaItem


        titleTextView.text = pet.nombre
        subtitleTextView.text = pet.raza

        thumbnailImageView.setImageResource(R.drawable.ic_baseline_person_24)

     /*   Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)*/



        return rowView
    }
}