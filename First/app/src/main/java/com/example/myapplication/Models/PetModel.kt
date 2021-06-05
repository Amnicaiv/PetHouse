package com.example.myapplication.Models

data class PetModel(val nombre:String,
                    val tipo:String,
                    val tamano:String,
                    val imagen: Int?=0)

data class FullPetModel(
        val ClienteId: String?,
        val Nombre:String,
        val Edad: Int,
        val CategoriaMascotaId:Int,
        val TipoMascotaId:Int)

data class Mascota(
        val userId:String?,
        val nombre:String,
        val edad:Int,
        val tipoMascotaId: Int,
        val categoriaMascotaId: Int,
        val imagen: String?,
        val cartillaPdf: String?,
        val fechaAlta: String
)