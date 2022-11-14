package com.example.tp2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicioUsuario {
    @GET("/api")
    fun getUsers(@Query("results") limit: String): Call<Users>
}