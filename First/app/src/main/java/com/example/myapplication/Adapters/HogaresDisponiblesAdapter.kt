package com.example.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.ImageParser
import com.example.myapplication.Models.listaHogaresDisponiblesModel
import com.example.myapplication.R

class HogaresDisponiblesAdapter(val context: Context, items:ArrayList<listaHogaresDisponiblesModel>) : BaseAdapter(){
    var items:ArrayList<listaHogaresDisponiblesModel>?=null
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
        var holder:HogarDisponibleViewHolder?=null
        var vista:View?=convertView

        if(vista==null)
        {
            vista = LayoutInflater.from(context).inflate(R.layout.item_hogar_disponible,null)
            holder = HogarDisponibleViewHolder(vista)
            vista.tag=holder
        }
        else
            holder = vista.tag as? HogarDisponibleViewHolder

        val item = getItem(position) as listaHogaresDisponiblesModel
        holder?.id?.text = item.id.toString()
        holder?.descripcion?.text = item.descripcion
        holder?.costoPorNoche?.text = item.costoPorNoche.toString()
        holder?.nombreDueno?.text = item.nombreDueno
        val encoder = ImageParser()
        val img = item.fotoLista?.let { encoder.convert(it) }
        holder?.imgLista?.setImageBitmap(img)

        return vista!!
    }

    private class HogarDisponibleViewHolder(vista:View){
        var id:TextView?=null
        var descripcion: TextView?=null
        var costoPorNoche: TextView?=null
        var capacidad: TextView?=null
        var nombreDueno:TextView?=null
        var imgLista:ImageView?=null
        init{
            id = vista.findViewById(R.id.tv_id_hogar)
            descripcion = vista.findViewById(R.id.tv_desc)
            costoPorNoche = vista.findViewById(R.id.tv_cpn)
            nombreDueno = vista.findViewById(R.id.tv_prop)
            imgLista = vista.findViewById(R.id.iv_img_lista)
        }
    }

}