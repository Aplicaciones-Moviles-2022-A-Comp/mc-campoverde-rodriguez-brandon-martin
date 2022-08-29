package com.example.proyecto_campoverdebrandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Productos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        init()
        val btnPrueba = findViewById<ImageButton>(R.id.btn_menu_productos)
        btnPrueba.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_productos)
            asd.openDrawer(GravityCompat.START)
        }
        setupSpiner()

    }

    fun setupSpiner(){
        var list:ArrayList<Categoria> = ArrayList<Categoria>()
        list.add((Categoria(R.drawable.ic_todo,"Todo")))
        list.add((Categoria(R.drawable.ic_frutas_y_verduras,"Frutas y Verduras")))
        list.add((Categoria(R.drawable.ic_carnes,"Carnes")))
        val spinner =  findViewById<Spinner>(R.id.sp_productos)
        val adapter = AdaptadorSpinner(this,list)
        spinner.adapter=adapter
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

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}