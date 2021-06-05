package com.example.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.Models.ReservacionLista
import com.example.myapplication.R

class MisReservacionesAdapter(val context: Context, items: ArrayList<ReservacionLista>) : BaseAdapter()  {
    var items:ArrayList<ReservacionLista>?=null

    init{
        this.items=items
    }

    override fun getCount(): Int{
        return items?.count()!!
    }

    override fun getItem(position:Int): Any{
        return items?.get(position)!!
    }

    override fun getItemId(position:Int):Long{
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var holder:ReservacionViewHolder
        // Get view for row item
        var vista: View? = convertView

        if(vista==null)
        {
            vista = LayoutInflater.from(context).inflate(R.layout.list_item_misreservaciones,null)
            holder = ReservacionViewHolder(vista)
            vista.tag=holder
        }
        else
            holder = vista.tag as ReservacionViewHolder

        val item = getItem(position) as ReservacionLista
        holder?.id?.text = item.id.toString()
        holder?.estatus?.text = item.estatus
        holder?.hogarNombre?.text = item.hogarNombre
        holder?.propietariaNombre?.text = item.propietariaNombre
        holder?.montoTotal?.text = item.montoTotal.toString()


        return vista!!
    }
    private class ReservacionViewHolder(vista: View){
        var id:TextView?=null
        var hogarNombre:TextView?=null
        var propietariaNombre:TextView?=null
        var montoTotal:TextView?=null
        var estatus:TextView?=null

        init{
            id = vista.findViewById(R.id.tv_reservacion_id)

        }
    }
}