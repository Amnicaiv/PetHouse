package com.example.myapplication


import com.example.myapplication.Models.*
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

    fun getUserData(url:String, callback: Callback):Call{
        val builder = FormBody.Builder()


        val request = Request.Builder()
                .url(url)
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

    fun getUser(url:String, callback:Callback):Call{
        val request = Request.Builder()
            .url(url)
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
        return call;
    }

    fun getPets(url:String, callback: Callback):Call{
        val request = Request.Builder()
            .url(url)
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun setPet(url:String, pet: Mascota, callback: Callback):Call{
        val builder=FormBody.Builder()

        val newPetJson = Gson().toJson(pet)
        println(newPetJson)

        val formBody = RequestBody.create(JSON,newPetJson.toString())
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        println(request)

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }


    fun setHouse(url:String, house: HouseModel, callback: Callback):Call{
        val builder=FormBody.Builder()

        val newHouseJson = Gson().toJson(house)
        println(newHouseJson)

        val formBody = RequestBody.create(JSON,newHouseJson.toString())
        val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

        println(request)

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun setReservation(url:String, reservacion: ReservacionModel, callback: Callback):Call{
        val builder=FormBody.Builder()

        val newReservJson = Gson().toJson(reservacion)
        println(newReservJson)

        val formBody = RequestBody.create(JSON,newReservJson.toString())
        val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

        println(request)

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun getUserReservations(url:String, callback: Callback):Call{
        val request = Request.Builder()
                .url(url)
                .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }


    fun getHogares(url:String,callback: Callback):Call{
        val request = Request.Builder()
            .url(url)
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

}