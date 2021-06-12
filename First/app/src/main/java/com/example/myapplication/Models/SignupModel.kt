package com.example.myapplication.Models

data class SignupModel(
    var name:String? = null,
    var pLastName:String? = null,
    var mLastName:String? = null,
    var birthDate:String? = null,
    var street:String? = null,
    var noExt:String? = null,
    var noInt:String? = null,
    var postalCode:String? = null,
    var ciudadId:Int = 3,
    var userName:String? = null,
    var email:String? = null,
    var phoneNumber:String? = null,
    var password:String? = null,
    var imagenPerfil:String?=null
)
