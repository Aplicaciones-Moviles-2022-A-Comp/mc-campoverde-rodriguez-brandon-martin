package com.example.proyecto_campoverdebrandon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Productos : AppCompatActivity(){

    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data

            }
        }
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if(res.resultCode== RESULT_OK){
            if (res.idpResponse!=null){
                this.seLogeo(res.idpResponse!!)
            }
        }
    }

    var arreglo = ArrayList<DataProductos>()
    var arregloCompras: MutableMap<String,ArrayList<String>> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        init()
        val btnPrueba = findViewById<ImageButton>(R.id.btn_menu_productos)
        btnPrueba.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_productos)
            asd.openDrawer(GravityCompat.START)
        }


        val reciclerProductos = findViewById<RecyclerView>(R.id.rv_productos)
        setupSpiner(reciclerProductos)
        val textNprods = findViewById<TextView>(R.id.tv_prodictosComprados)
        textNprods.text=arregloCompras.size.toString()
        val btnIrCarrito = findViewById<ImageButton>(R.id.btn_ir_carrito)
        btnIrCarrito.setOnClickListener {
            abrirActividadParametros(Carrito::class.java)
        }

        val btnLogin = findViewById<Button>(R.id.btn_iniciar_sesion_productos)
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

        val btnLogout = findViewById<Button>(R.id.btn_cerrar_sesion_productos)
        btnLogout.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    seDeslogueo()
                }
        }
    }

    fun aniadirProducto(
        arregloNuevo: ArrayList<DataProductos>,
        producto: QueryDocumentSnapshot
    ) {
        arregloNuevo.add(
            DataProductos(
                producto.get("nombre") as String,
                producto.get("descripcion") as String,
                producto.get("precio") as Double,
                producto.get("stock") as Long,
                producto.get("imagen") as String,
                producto.get("tipo") as String
            )
        )
    }

    fun setupSpiner(recyclerView: RecyclerView){
        var list:ArrayList<Categoria> = ArrayList<Categoria>()
        list.add((Categoria(R.drawable.ic_todo,"Todo")))
        list.add((Categoria(R.drawable.ic_frutas_y_verduras,"Frutas y Verduras")))
        list.add((Categoria(R.drawable.ic_carnes,"Carnes")))
        list.add((Categoria(R.drawable.ic_lacteo,"Lacteos")))
        val spinner =  findViewById<Spinner>(R.id.sp_productos)
        val adapter = AdaptadorSpinner(this,list)
        spinner.adapter=adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItm = parent?.getItemAtPosition(position)
                var asd = getCategotia(selectedItm as Categoria)
                cargarProductosCategoria(recyclerView,asd)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    fun init(){
        val menuNav = findViewById<NavigationView>(R.id.nav_productos)
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

    fun cargarProductosInicio(
        recyclerView: RecyclerView
    ){
        val db = Firebase.firestore
        val soRefUnico = db
            .collection("productos")
        soRefUnico
            .get()
            .addOnSuccessListener {
                arreglo = ArrayList<DataProductos>()
                for (prod in it) {
                    aniadirProducto(arreglo, prod)
                }
                inicializarRV(arreglo,recyclerView)
            }

    }

    fun cargarProductosCategoria(
        recyclerView: RecyclerView
    , categoria: String
    ){
        if(categoria=="Todo"){
            cargarProductosInicio(recyclerView)
        }else {
            val db = Firebase.firestore
            val soRefUnico = db
                .collection("productos").whereEqualTo("tipo", categoria)
            soRefUnico
                .get()
                .addOnSuccessListener {
                    arreglo = ArrayList<DataProductos>()
                    for (prod in it) {
                        aniadirProducto(arreglo, prod)
                    }
                    inicializarRV(arreglo, recyclerView)
                }
        }
    }

    fun inicializarRV(
        lista:ArrayList<DataProductos>,
        recyclerView: RecyclerView
    ){
        val adaptador = AdaptadorProductos(
            this,
            lista,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun getCategotia(producto: Categoria): String {
        return "" + producto.categoria
    }

    fun agregaralcarrito(llave:String,cantidad:Long,precio:Double){
        arregloCompras[llave]= arrayListOf(cantidad.toString(),precio.toString())
        val textNprods = findViewById<TextView>(R.id.tv_prodictosComprados)
        textNprods.text=arregloCompras.size.toString()
        abrirDialogo()
        }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Añadido con exito")
        builder.setPositiveButton(
            "Aceptar",
            null
        )

        val dialog = builder.create()
        dialog.show()
    }

    fun abrirActividadParametros(
        clase:Class<*>
    ){
        var aux:ArrayList<CarritoData> = ArrayList<CarritoData>()
        for((clave,valor) in arregloCompras){
            aux.add(
                CarritoData(
                clave,valor[0].toLong(),valor[1].toDouble()
                )
            )
        }
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("carrito",aux)

        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
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