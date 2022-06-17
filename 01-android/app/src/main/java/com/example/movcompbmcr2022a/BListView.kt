package com.example.movcompbmcr2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListView : AppCompatActivity() {
    val arreglo: ArrayList<Int> = arrayListOf(1,2,3,4,5)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAnadirListVies = findViewById<Button>(
            R.id.brt_anadir_list_view
        )
        botonAnadirListVies.setOnClickListener {
            anadirNumero(adaptador)
        }
    }

    fun anadirNumero(
        adaptador: ArrayAdapter<Int>
    ){
        arreglo.add(1)
        adaptador.notifyDataSetChanged()
    }
}