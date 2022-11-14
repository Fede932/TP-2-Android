package com.example.tp2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UsuarioAdapter (
    private val context: Context,
    private val usuarioClickeado: (DatosUsuario) -> Unit
    ) : RecyclerView.Adapter<UsuarioVH>()

    {

        private var usuarios: List<DatosUsuario> = emptyList()

        fun establecerUsuario(usuarios: List<DatosUsuario>) {
            this.usuarios = usuarios

            this.notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioVH {

            val inflater = LayoutInflater.from(context)
            val vista = inflater.inflate(
                R.layout.item_usuario,
                parent,
                false
            )
            return UsuarioVH(vista)
        }

        override fun onBindViewHolder(holder: UsuarioVH, position: Int) {
            val usuario = usuarios[position]

            holder.name.text = usuario.name
            holder.country.text = usuario.country
            holder.age.text = usuario.age.toString()
            Glide.with(context).load(usuario.image.small).into(holder.image)

            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    usuarioClickeado(usuario)
                }
            })
        }

        override fun getItemCount(): Int {
            return usuarios.size
        }
}