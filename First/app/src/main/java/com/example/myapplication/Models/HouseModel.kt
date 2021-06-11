package com.example.myapplication.Models

class HouseModel (
        val userId: String?,
        val descripcion: String,
        val costoPorNoche: Double,
        val capacidad: Int,
        val cuentaConMascotas: Boolean,
        val publicado: Boolean,
        val disponible: Boolean,
        val pausado: Boolean,
        val fechaAlta: String,
        val foto1:String?,
        val foto2:String?,
        val foto3:String?
)

class listaHogarModel(val id:Int, val descripcion:String, val costoPorNoche: Double, val capacidad: Int, val fotoLista:String?)

class listaHogaresDisponiblesModel(val id:Int, val descripcion:String, val costoPorNoche:Double, val capacidad:Int, val nombreDueno:String, val fotoLista: String?)