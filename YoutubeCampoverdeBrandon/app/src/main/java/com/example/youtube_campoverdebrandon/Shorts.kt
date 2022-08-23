package com.example.youtube_campoverdebrandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Shorts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shorts)

        val listaShorts = arrayListOf<ShortsPiratas>()
        listaShorts.add(ShortsPiratas("Músicos hablando","Suena like Lucho",
            "https://yt3.ggpht.com/ytc/AMLnZu-KSGiEv4V9O5chm4UFMLqfrCZx6bBQvsqU6IFvvyc=s48-c-k-c0x00ffffff-no-rj",
            "https://i.ytimg.com/vi/48x7O9Ca2e4/hq720_2.jpg?sqp=-oaymwEkCJYDENAFSFryq4qpAxYIARUAAAAAJQAAyEI9AICiQ3gB0AEB&rs=AOn4CLCz9d9WGn39BZTIZjhynMpG0ppmuw",
        "https://www.youtube.com/shorts/gTOEnPxoK0Y"))

        listaShorts.add(ShortsPiratas("AZOTAR PUERTAS","Elabrahaham",
            "https://yt3.ggpht.com/wbZMWqSzs76Py9bwsB105FXdp8AYCpKSeXd06jCoHbmw0xK69RfB324wNgNeTpYdkClmhZBUjQ=s48-c-k-c0x00ffffff-no-rj",
            "https://i.ytimg.com/vi/bfsFeT0z1C8/hq720_2.jpg?sqp=-oaymwEkCJYDENAFSFryq4qpAxYIARUAAAAAJQAAyEI9AICiQ3gB0AEB&rs=AOn4CLAWV_XaB5f2k8v4JfDlZk-vPJ4KGg",
            "https://www.youtube.com/shorts/d_LxFmrMbQ8"))

        val recyclerView = findViewById<RecyclerView>(R.id.rv_videos_short)
        inicializarRV(listaShorts,recyclerView)

        val navmenu = findViewById<BottomNavigationView>(R.id.nav_menu_yt1)
        navmenu.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.act_principal -> {
                    irActividad(MainActivity::class.java)
                    true
                }
                R.id.act_shorts -> {
                    irActividad(Shorts::class.java)
                    true
                }
                else -> false
            }
        }
    }

    fun inicializarRV(
        lista:ArrayList<ShortsPiratas>,
        recyclerView:RecyclerView
    ){
        val adaptador = AdaptadorShorts(
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
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}