package com.example.tp2

data class DatosUsuario(
    val name: String,
    val age: Int,
    val email: String,
    val image: UserImage,
    val country: String,
    val telephone: String,
    val location: Location,
)

data class UserImage(
    val small: String,
    val large: String
)

data class Location(
    val address: String,
    val latitude: String,
    val longitude: String
)