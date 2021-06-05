package com.example.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.ImageParser
import com.example.myapplication.Models.PetModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.layout_petinfo.*
import kotlinx.android.synthetic.main.list_template.view.*
import org.w3c.dom.Text

class CustomAdapter(var context:Context, items:ArrayList<PetModel>) : BaseAdapter() {
    var items:ArrayList<PetModel>? = null

    init{
        this.items=items
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    override fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder? = null

        var vista:View? = convertView

        if(vista==null){
            vista = LayoutInflater.from(context).inflate(R.layout.list_template, null)
            holder = ViewHolder(vista)
            vista.tag=holder
        }
        else{
            holder = vista.tag as? ViewHolder
        }

        val  item = getItem(position) as  PetModel
        holder?.nombre?.text = item.nombre
        holder?.tipo?.text = item.tipo
        holder?.tamano?.text = item.tamano
        val encoder = ImageParser()
        val img = item.imagen?.let { encoder.convert(it) }
        holder?.imagen?.setImageBitmap(img)

        return vista!!
    }

    private class ViewHolder(vista:View){
        var nombre:TextView? = null
        var tipo:TextView?=null
        var tamano:TextView?=null
        var imagen:ImageView?=null

        init{
            nombre = vista.findViewById(R.id.nombre)
            tipo = vista.findViewById(R.id.tipo)
            tamano = vista.findViewById(R.id.tamano)
            imagen = vista.findViewById(R.id.imagen)
        }
    }
}