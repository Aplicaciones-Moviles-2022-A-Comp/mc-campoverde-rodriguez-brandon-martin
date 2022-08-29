package com.example.proyecto_campoverdebrandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class Sucursales : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucursales)
        init()

        val btnAbrirMenu = findViewById<ImageButton>(R.id.btn_menu_locales)
        btnAbrirMenu.setOnClickListener {
            var asd = findViewById<DrawerLayout>(R.id.dl_locales)
            asd.openDrawer(GravityCompat.START)
        }

        val caroselQuito: ImageCarousel = findViewById(R.id.carousel_quito)
        caroselQuito.registerLifecycle(lifecycle)
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
        caroselQuito.setData(listaoferta)

        val caroselGuayaquil: ImageCarousel = findViewById(R.id.carousel_guayaquil)
        caroselGuayaquil.registerLifecycle(lifecycle)
        val listaoferta1 = mutableListOf<CarouselItem>()
        listaoferta1.add(
            CarouselItem(
                imageDrawable = R.drawable.imagen1
            )
        )
        listaoferta1.add(
            CarouselItem(
                imageDrawable = R.drawable.oferta1
            )
        )
        listaoferta1.add(
            CarouselItem(
                imageDrawable = R.drawable.oferta2
            )
        )
        caroselGuayaquil.setData(listaoferta1)

        val caroselCuenca: ImageCarousel = findViewById(R.id.carousel_cuenta)
        caroselCuenca.registerLifecycle(lifecycle)
        val listaoferta2 = mutableListOf<CarouselItem>()
        listaoferta2.add(
            CarouselItem(
                imageDrawable = R.drawable.imagen1
            )
        )
        listaoferta2.add(
            CarouselItem(
                imageDrawable = R.drawable.oferta1
            )
        )
        listaoferta2.add(
            CarouselItem(
                imageDrawable = R.drawable.oferta2
            )
        )
        caroselCuenca.setData(listaoferta2)

    }

    fun init(){
        val menuNav = findViewById<NavigationView>(R.id.nav_locales)
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