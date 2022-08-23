package com.example.proyecto_campoverdebrandon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Contactanos : AppCompatActivity() {

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
        setContentView(R.layout.activity_contactanos)
        init()
        val btnAbrirMenu = findViewById<ImageButton>(R.id.btn_menu_contactanos)
        btnAbrirMenu.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_contactanos)
            asd.openDrawer(GravityCompat.START)
        }
        val btnEnviarCont = findViewById<Button>(R.id.btn_enviar_cont)
        btnEnviarCont.setOnClickListener {
            val editTextNombre = findViewById<EditText>(R.id.et_nombre_cont)
            val editTextTelefono = findViewById<EditText>(R.id.et_telefono_cont)
            val editTextCorreo = findViewById<EditText>(R.id.et_correo_cont)
            val editTextMensaje = findViewById<EditText>(R.id.et_mensaje_cont)
            guardarComentario(
                editTextNombre.text.toString(),
                editTextTelefono.text.toString(),
                editTextCorreo.text.toString(),
                editTextMensaje.text.toString()
            )
            abrirActividadParametros(Agradecimiento::class.java)
        }
    }

    fun init(){
        val menuNav = findViewById<NavigationView>(R.id.nav_contactanos)
        menuNav.setNavigationItemSelectedListener{ item->
            when(item.itemId){
                R.id.menu_inicio -> {
                    irActividad(MainActivity::class.java)
                    true
                }
                R.id.menu_productos -> {

                    true
                }
                R.id.menu_locales -> {

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

    fun guardarComentario(nombre:String,telefono:String,correo:String,mensaje:String){
        val db = Firebase.firestore
        val so = db.collection("comentarios")
        val identificador = Date().time
        val datosComentario = hashMapOf(
            "idComentario" to identificador,
            "nombre" to nombre,
            "telefono" to telefono,
            "correo" to correo,
            "mensaje" to mensaje
        )
        so.document(identificador.toString()).set(datosComentario)

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadParametros(
        clase:Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("agradecimiento", "¡Gracias por\nsu comentario!")
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }

}