package com.example.myapplication.Models

data class UserModel(
        val id:String,
        val name:String,
        val email:String,
        val state:Int,
        val city:Int,
        val blocked:Boolean,
        val accessToken:String?,
        val imgProfile:String?
)
