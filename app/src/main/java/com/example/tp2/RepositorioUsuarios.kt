package com.example.tp2


import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RepositorioUsuarios {

    private val BASE_URL = "https://randomuser.me/"
    private val servicio: ServicioUsuario
    private lateinit var usuarios: List<DatosUsuario>


    init {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val converterMoshiRetrofit = MoshiConverterFactory.create(moshi)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterMoshiRetrofit)
            .build()

        servicio = retrofit.create(ServicioUsuario::class.java)
    }

    fun getLimitUsers(
        limit: Int,
        callbackResult: (List<DatosUsuario>) -> Unit,
        callbackError: (Throwable) -> Unit
    ) {
        Log.d("PARAMETER", limit.toString())
        servicio.getUsers(limit = limit.toString()).enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                response: Response<Users>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()


                    if (body == null) {
                        throw IllegalStateException("Error getting users")
                    }
                    Log.d("UserRepository  Body", body.toString())

                    usuarios = body.results.map { DatosLista -> mapUsuarios(DatosLista) }
                    callbackResult(usuarios)
                }

            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.d("UserRespository ERROR", call.toString())
                callbackError(t)
            }

        })
    }

    private fun mapUsuarios( datosLista: DatosLista): DatosUsuario {
        return DatosUsuario(
            name = "${datosLista.name.first} ${datosLista.name.last}",
            age = datosLista.dob.age,
            email = datosLista.email,
            image = UserImage(
                small = datosLista.picture.thumbnail,
                large = datosLista.picture.large
            ),
            country = datosLista.location.country,
            telephone = datosLista.phone,
            location = Location(
                address = "${datosLista.location.street.name} ${datosLista.location.street.number}",
                latitude = datosLista.location.coordinates.latitude,
                longitude = datosLista.location.coordinates.longitude,
            )
        );
    }

    fun getUserByEmail(email: String): DatosUsuario? {
        return usuarios.find { usuarios -> usuarios.email == email }
    }


}