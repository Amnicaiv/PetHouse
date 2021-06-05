package com.example.myapplication.Models

class HouseModel (
        val userId: String?,
        val descripcion: String,
        val costoPorNoche: Int,
        val capacidad: Int,
        val cuentaConMascotas: Boolean,
        val publicado: Boolean,
        val disponible: Boolean,
        val pausado: Boolean,
        val fechaAlta: String
)