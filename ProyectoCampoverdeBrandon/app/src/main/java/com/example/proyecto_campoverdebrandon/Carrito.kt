package com.example.proyecto_campoverdebrandon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class Carrito : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if(res.resultCode== RESULT_OK){
            if (res.idpResponse!=null){
                this.seLogeo(res.idpResponse!!)
            }
        }
    }

    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)
        val listacarrito = intent.getParcelableArrayListExtra<CarritoData>("carrito")
        val rvCarrito = findViewById<RecyclerView>(R.id.rv_carrito)
        inicializarRV(listacarrito!!,rvCarrito)

        init()
        val btnPrueba = findViewById<ImageButton>(R.id.btn_menu_carrito)
        btnPrueba.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_carrito)
            asd.openDrawer(GravityCompat.START)
        }
        val navigationView:NavigationView = findViewById(R.id.nav_carrito)
        val header: View = navigationView.inflateHeaderView(R.layout.nav_header_menu)
        val btnAtras = header.findViewById<ImageButton>(R.id.btn_menu_atras)
        btnAtras.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_carrito)
            asd.closeDrawer(GravityCompat.START)
        }

        val btnLogin = findViewById<Button>(R.id.btn_iniciar_sesion_carrito)
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

        val btnLogout = findViewById<Button>(R.id.btn_cerrar_sesion_carrito)
        btnLogout.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    seDeslogueo()
                }
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_carrito)
        btnCancelar.setOnClickListener {
            irActividad(Productos::class.java)
        }

        val btnPagar = findViewById<Button>(R.id.btn_pagar_carrito)
        btnPagar.setOnClickListener {
            val totalPagar = findViewById<TextView>(R.id.tv_set_tota_pagar)
            guardarFactura(listacarrito,totalPagar.text.toString().toDouble())
            abrirActividadParametros(Agradecimiento::class.java)
        }

    }

    fun abrirActividadParametros(
        clase:Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("agradecimiento", "¡Gracias por\npor comprar!")
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }

    fun guardarFactura(compras:ArrayList<CarritoData>,totalPagar:Double){
        val db = Firebase.firestore
        val so = db.collection("facturas")
        val identificador = Date().time
        val datosComentario = hashMapOf(
            "idCompra" to identificador,
            "compras" to compras,
            "totalapagar" to totalPagar,
        )
        so.document(identificador.toString()).set(datosComentario)

    }

    fun inicializarRV(
        lista:ArrayList<CarritoData>,
        recyclerView: RecyclerView
    ){
        val adaptador = AdaptadorCarrito(
            this,
            lista,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    fun seLogeo(
        res: IdpResponse
    ){
        val btnLogin = findViewById<Button>(R.id.btn_iniciar_sesion_carrito)
        val btnLogout = findViewById<Button>(R.id.btn_cerrar_sesion_carrito)
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
        val btnLogin = findViewById<Button>(R.id.btn_iniciar_sesion_carrito)
        val btnLogout = findViewById<Button>(R.id.btn_cerrar_sesion_carrito)
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
    }

    fun init(){
        val menuNav = findViewById<NavigationView>(R.id.nav_carrito)
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
                else -> false
            }
        }
    }
    var precioTotal:Double = 0.00
    fun calcularPrecioTotal(precio:Double){
        precioTotal = precioTotal+precio
        val textmostrar = findViewById<TextView>(R.id.tv_set_tota_pagar)
        textmostrar.text= precioTotal.toString()
        //textmostrar.text="Perro"
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}