package com.example.youtube_campoverdebrandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Shorts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shorts)

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


    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}