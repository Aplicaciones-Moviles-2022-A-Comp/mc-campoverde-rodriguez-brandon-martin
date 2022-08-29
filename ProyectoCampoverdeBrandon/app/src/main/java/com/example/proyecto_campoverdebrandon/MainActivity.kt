package com.example.proyecto_campoverdebrandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        val btnPrueba = findViewById<ImageButton>(R.id.btn_menu_home)
        btnPrueba.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_home)
            asd.openDrawer(GravityCompat.START)
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
                imageDrawable = R.drawable.oferta1
            )
        )
        listaoferta.add(
            CarouselItem(
                imageDrawable = R.drawable.oferta2
            )
        )
        caroselHome.setData(listaoferta)
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