package com.example.tp2

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioVH (view: View): RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.imagen)
    val name: TextView = view.findViewById(R.id.nombre)
    val country: TextView = view.findViewById(R.id.pais)
    val age: TextView = view.findViewById(R.id.edad)
}