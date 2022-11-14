package com.example.tp2

data class Users(
    val results: List<DatosLista>

)

data class InfoResponse(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)