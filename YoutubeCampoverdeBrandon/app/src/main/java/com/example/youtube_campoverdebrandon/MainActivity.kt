package com.example.youtube_campoverdebrandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_principal_yt)
        initRV(recyclerView)
        val navmenu = findViewById<BottomNavigationView>(R.id.nav_menu_yt)
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

    fun initRV(recyclerView:RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdaptadorPrincipal(ProveedorVideosYT.videosYTList)
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}

