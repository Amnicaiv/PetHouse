package com.example.myapplication.Models

class ReservacionModel (

        val userId: String?,
        val hogarId: Int,
        val estatusReservacionId: Int,
        val fechaEntrada: String,
        val fechaSalida: String,
        val montoTotal: Int,
        val recibeAlimento: Boolean,
        val descripcionAlimento: String,
        val comentariosCliente: String
)

class MiReservacionModel(

        val id:Int?,
        val hogarId:Int?,
        val hogarNombre:String,
        val propietariaNombre:String,
        val fechaEntrada:String,
        val fechaSalida:String,
        val montoTotal:Int,
        val comentarios:String,
        val estatus:String
)

class ReservacionLista(
        val id:Int,
        val hogarNombre:String,
        val propietariaNombre:String,
        val montoTotal:Double,
        val estatus:String,
        val fechaEntrada: String,
        val fechaSalida: String
)