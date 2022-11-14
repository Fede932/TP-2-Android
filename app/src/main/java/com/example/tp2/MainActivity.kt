package com.example.tp2

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ConditionVariable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.os.postDelayed
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.request.transition.ViewPropertyTransition

class MainActivity : AppCompatActivity() {
    private lateinit var repoUsuario: RepositorioUsuarios
    private lateinit var lista: RecyclerView
    private lateinit var adapter: UsuarioAdapter
    private lateinit var swipe: SwipeRefreshLayout
    private val INTERNET_REQUEST_CODE = 666



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loading = Loading(this)
        loading.startLoad()
        Handler(Looper.getMainLooper()).postDelayed({
            loading.isDismiss()

        }, 6000)

        repoUsuario = RepositorioUsuarios()

        lista = findViewById(R.id.lista_usuarios)
        swipe = findViewById(R.id.swipe)


        adapter = UsuarioAdapter(this) { usuario ->
            val intent = Intent(this, ItemUsuario::class.java)
            intent.putExtra("name", usuario.name)
            intent.putExtra("email", usuario.email)
            intent.putExtra("phone", usuario.telephone)
            intent.putExtra("image", usuario.image.large)
            intent.putExtra("address", usuario.location.address)
            startActivity(intent)
        }

        lista.adapter = adapter

        checkAppPermissions()

        swipe.setOnRefreshListener {
            swipe.isRefreshing = true
            getUsers()
        }

        getUsers()

    }

    private fun checkAppPermissions() {
        if (!checkPermission(Manifest.permission.INTERNET)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                INTERNET_REQUEST_CODE
            )
        } else {
            Log.d("Internet:", "Permission granted")
        }
    }


    private fun checkPermission(permission: String): Boolean {
        return PermissionChecker.checkSelfPermission(
            this,
            permission
        ) == PermissionChecker.PERMISSION_GRANTED
    }

    private fun getUsers() {
        repoUsuario.getLimitUsers(12,
            callbackError = { t -> showError(t) } ,
            callbackResult = { users -> showUsers(users) })

    }

    private fun showUsers(users: List<DatosUsuario>) {
        Log.d("USERS LIST", users.toString())
        adapter.establecerUsuario(users)

        swipe.isRefreshing = false
    }


    private fun showError(error: Throwable) {
        Log.e("GET USER ERROR", "No se pudo obtener los usuarios", error)
        Toast.makeText(this, "No se pudo obtener los usuarios", Toast.LENGTH_LONG).show()
    }

}

