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

data class UpdateModel(
        var id:String?=null,
        var name:String? = "",
        var pLastName:String? = "",
        var mLastName:String? = "",
        var birthDate:String? = "",
        var street:String? = "",
        var noExt:String? = "",
        var noInt:String? = "",
        var postalCode:String? = "",
        var ciudadId:Int = 3,
        var userName:String? = "",
        var email:String? = "",
        var phoneNumber:String? = "",
        var passwordHash:String? = "",
        var imagenPerfil:String?=""
)


