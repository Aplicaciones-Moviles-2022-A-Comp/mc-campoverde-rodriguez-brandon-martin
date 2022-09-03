package com.example.proyecto_campoverdebrandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class MainActivity : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if(res.resultCode== RESULT_OK){
            if (res.idpResponse!=null){
                this.seLogeo(res.idpResponse!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        val btnPrueba = findViewById<ImageButton>(R.id.btn_menu_home)
        btnPrueba.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_home)
            asd.openDrawer(GravityCompat.START)
        }
        val navigationView:NavigationView = findViewById(R.id.nav_inicio)
        val header: View = navigationView.inflateHeaderView(R.layout.nav_header_menu)
        val btnAtras = header.findViewById<ImageButton>(R.id.btn_menu_atras)
        btnAtras.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_home)
            asd.closeDrawer(GravityCompat.START)
        }

        val caroselHome:ImageCarousel = findViewById(R.id.carousel_home)
        caroselHome.registerLifecycle(lifecycle)
        val listaoferta = mutableListOf<CarouselItem>()
        listaoferta.add(
            CarouselItem(
                imageDrawable = R.drawable.imagen1
            )
        )
        listaoferta.add(
            CarouselItem(
                imageDrawable = R.drawable.home2
            )
        )
        listaoferta.add(
            CarouselItem(
                imageDrawable = R.drawable.home3
            )
        )
        caroselHome.setData(listaoferta)

        val btnLogin = findViewById<Button>(R.id.btn_iniciar_sesion_home)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build())

// Create and launch sign-in intent
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)

        }

        val btnLogout = findViewById<Button>(R.id.btn_cerrar_sesion_home)
        btnLogout.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    seDeslogueo()
                }
        }

    }

    fun init(){
        val menuNav = findViewById<NavigationView>(R.id.nav_inicio)
        menuNav.setNavigationItemSelectedListener{ item->
             when(item.itemId){
                 R.id.menu_inicio -> {
                     irActividad(MainActivity::class.java)
                     true
                 }
                 R.id.menu_productos -> {
                    irActividad(Productos::class.java)
                     true
                 }
                 R.id.menu_locales -> {
                     irActividad(Sucursales::class.java)
                     true
                 }
                 R.id.menu_contactanos -> {
                     irActividad(Contactanos::class.java)
                     true
                 }
                 R.id.btn_menu_atras ->{
                     var asd = findViewById<DrawerLayout>(R.id.dl_home)
                     asd.closeDrawer(GravityCompat.START)
                     true
                 }
                 else -> false
             }
        }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun seLogeo(
        res: IdpResponse
    ){
        val btnLogin = findViewById<Button>(R.id.btn_iniciar_sesion_home)
        val btnLogout = findViewById<Button>(R.id.btn_cerrar_sesion_home)
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        if(res.isNewUser==true){
            registarusuarioPrimeraVez(res)
        }
    }

    fun registarusuarioPrimeraVez(
        usuario: IdpResponse
    ){
        val usuarioLogeado = FirebaseAuth.getInstance().currentUser
        if(usuario.email != null && usuarioLogeado != null){
            val db = Firebase.firestore
            val roles = arrayListOf("usuario")
            val email = usuario.email
            val uid = usuarioLogeado.uid
            val nuevoUsuario = hashMapOf<String,Any>(
                "roles" to roles,
                "uid" to uid,
                "email" to email.toString()
            )
            db.collection("usuario").document(email.toString())
                .set(nuevoUsuario).addOnSuccessListener {

                }.addOnFailureListener{

                }
        }
    }

    fun seDeslogueo(){
        val btnLogin = findViewById<Button>(R.id.btn_iniciar_sesion_home)
        val btnLogout = findViewById<Button>(R.id.btn_cerrar_sesion_home)
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
    }

}