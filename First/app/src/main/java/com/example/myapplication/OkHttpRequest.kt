package com.example.myapplication


import com.example.myapplication.Models.SignupModel
import com.example.myapplication.Models.loginModel
import com.google.gson.Gson
import okhttp3.*

class OkHttpRequest(client:OkHttpClient) {
    companion object{
        val JSON = MediaType.parse("application/json")
    }

    private var client = OkHttpClient()

    init{
        this.client = client
    }

    fun login(url:String, login:loginModel, callback: Callback):Call{
        val builder = FormBody.Builder()

        val userJson = Gson().toJson(login)
        println(userJson)

        val formBody = RequestBody.create(JSON, userJson.toString())
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        println(request)

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun signup(url:String, signup:SignupModel, callback: Callback):Call{
        val builder=FormBody.Builder()

        val newUserJson = Gson().toJson(signup)
        println(newUserJson)

        val formBody = RequestBody.create(JSON,newUserJson.toString())
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        println(request)

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

}