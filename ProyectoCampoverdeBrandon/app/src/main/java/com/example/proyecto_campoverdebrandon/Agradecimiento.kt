package com.example.proyecto_campoverdebrandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Agradecimiento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agradecimiento)
        init()
        val btnAbrirMenu = findViewById<ImageButton>(R.id.btn_menu_agradecimiento)
        btnAbrirMenu.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_agradecimiento)
            asd.openDrawer(GravityCompat.START)
        }
        val agradecimiento = intent.getStringExtra("agradecimiento")
        val tvMostrarAgradecimiento = findViewById<TextView>(R.id.tv_mostrar_agradecimiento)
        tvMostrarAgradecimiento.setText(agradecimiento)
        val btnAceptarMensaje = findViewById<Button>(R.id.btn_aceptar_mensaje)
        btnAceptarMensaje.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
    }

    fun init(){
        val menuNav = findViewById<NavigationView>(R.id.nav_agradecimiento)
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

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}