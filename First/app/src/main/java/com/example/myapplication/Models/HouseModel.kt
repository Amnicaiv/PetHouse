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
        val fechaAlta: String
)

class listaHogarModel(val descripcion:String, val costoPorNoche: Double, val capacidad: Int)

class listaHogaresDisponiblesModel(val id:Int, val descripcion:String, val costoPorNoche:Double, val capacidad:Int, val nombreDueno:String)