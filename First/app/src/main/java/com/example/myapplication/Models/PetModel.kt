package com.example.myapplication.Models

import java.util.*

data class PetModel(val nombre:String,
                    val tipo:String,
                    val tamano:String,
                    val imagen: Int?=0)

data class FullPetModel(
    val ClienteId:Int,
    val Nombre:String,
    val Edad: Int,
    val CategoriaMascotaId:Int,
    val TipoMascotaId:Int)
