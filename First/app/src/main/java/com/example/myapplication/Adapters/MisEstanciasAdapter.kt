package com.example.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.EstanciaModel
import com.example.myapplication.Models.listaHogarModel
import com.example.myapplication.R


class MisEstanciasAdapter(val context: Context, items: ArrayList<listaHogarModel>) : BaseAdapter()  {
    var items:ArrayList<listaHogarModel>?=null

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



 override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
     var holder:EstanciaViewHolder?=null
     // Get view for row item
     var vista:View? = convertView

     if(vista==null)
     {
         vista = LayoutInflater.from(context).inflate(R.layout.list_item_miresidencia,null)
         holder =EstanciaViewHolder(vista)
         vista.tag=holder
     }
     else
         holder = vista.tag as? EstanciaViewHolder

     val item = getItem(position) as listaHogarModel
     holder?.descripcion?.text = item.descripcion
     holder?.costoPorNoche?.text = item.costoPorNoche.toString()

     return vista!!
 }
    private class EstanciaViewHolder(vista:View){
        var descripcion:TextView?=null
        var costoPorNoche:TextView?=null

        init{
            descripcion = vista.findViewById(R.id.tv_descripcion)
            costoPorNoche = vista.findViewById(R.id.tv_costoPorNoche)
        }
    }
}
