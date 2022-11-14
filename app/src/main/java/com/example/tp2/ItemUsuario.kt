package com.example.tp2

import android.Manifest
import android.content.ClipDescription
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.bumptech.glide.Glide

class ItemUsuario : AppCompatActivity() {

    private lateinit var repoUsuario: RepositorioUsuarios
//    private lateinit var usuario: DatosUsuario // Este Usuario no existe en esta parte
    private val COD_PERMISO_LLAMADA = 29384757
    private val permisoDeLlamada = Manifest.permission.CALL_PHONE
    private lateinit var phone: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_usuario)

//        repoUsuario = RepositorioUsuarios()  // JM - COMENTADO // no hace falta inicializar el repo porque la data llega por los StringExtra
//
//        phone = usuario.telephone  // JM - COMENTADO // Esta data la sacas de los extra
//        name = usuario.name  // JM - COMENTADO // Esta data la sacas de los extra


        val userName = intent.getStringExtra("name")
        if (userName == null) {
            throw IllegalArgumentException("No se obtuvo usuario")
        }
        val userMail = intent.getStringExtra("email")
        if (userMail == null) {
            throw IllegalArgumentException("No se obtuvo usuario")
        }
        val userAddres = intent.getStringExtra("address")
        if (userAddres == null) {
            throw IllegalArgumentException("No se obtuvo usuario")
        }
        val userPhone = intent.getStringExtra("phone")
        if (userPhone == null) {
            throw IllegalArgumentException("No se obtuvo usuario")
        }

//       JM AGREGADO // Faltaba obtener la imagen de los extra
        val userImage = intent.getStringExtra("image")
        if (userImage == null) {
            throw IllegalArgumentException("No se obtuvo usuario")
        }

        phone = userPhone  // JM - AGREGADO
        name = userName  // JM - AGREGADO // Aca seteo las var con la data del extra

        // val quizaUsuario = repoUsuario.getUserByEmail(user)
        // if (quizaUsuario == null) {
        //     throw IllegalArgumentException("No hay usuario con ese email $user")
        //   } else {
        //         user = DatosUsuario(this)
        //       }

        val nombre: TextView = findViewById(R.id.item_nombre)
//        nombre.text = usuario.name // JM - COMENTADO
        nombre.text = userName // JM - AGREGADO

        val mail: TextView = findViewById(R.id.item_mail)
//        mail.text = usuario.email // JM - COMENTADO
        mail.text = userMail // JM - AGREGADO

        val contacto: TextView = findViewById(R.id.item_phone)
//        contacto.text = usuario.telephone // JM - COMENTADO
        contacto.text = userPhone // JM - AGREGADO

        val direccion: TextView = findViewById(R.id.item_direccion)
//        direccion.text = usuario.location.address // JM - COMENTADO
        direccion.text = userAddres // JM - AGREGADO

        val imagen: ImageView = findViewById(R.id.item_imagen)
//        Glide.with(this).load(usuario.image).into(imagen) // JM - COMENTADO
        Glide.with(this).load(userImage).into(imagen) // JM - AGREGADO

        val iniciar = findViewById<Button>(R.id.llamar)
        iniciar.setOnClickListener {
            val resultadoPermiso = PermissionChecker.checkSelfPermission(this, permisoDeLlamada)

            if (resultadoPermiso == PermissionChecker.PERMISSION_GRANTED) {
                marcarNumero("$contacto")
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permisoDeLlamada),
                    COD_PERMISO_LLAMADA
                )
            }
        }
        setListeners()
    }
    private fun setListeners() {
    val msj = findViewById<Button>(R.id.mensaje)

        msj.setOnClickListener {
            sendMessage("Hola ${name}")
        }
    }

    private fun sendMessage(mensaje: String) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, mensaje)
        intent.type = ClipDescription.MIMETYPE_TEXT_PLAIN
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager.MATCH_ALL
        } else {
            0
        }

        val resolveInfos = packageManager.queryIntentActivities(intent, flag)
        if (resolveInfos.size == 0) {
            Toast.makeText(this, "No tiene apps para mandar el mensaje", Toast.LENGTH_LONG).show()
        } else {
            startActivity(intent)
        }


}
    private fun marcarNumero(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse("tel:$phone")

        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == COD_PERMISO_LLAMADA) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                marcarNumero("phone")
            } else {
                Toast.makeText(this, "Se denegó el permiso de llamada", Toast.LENGTH_LONG).show()
            }
        }

    }
}