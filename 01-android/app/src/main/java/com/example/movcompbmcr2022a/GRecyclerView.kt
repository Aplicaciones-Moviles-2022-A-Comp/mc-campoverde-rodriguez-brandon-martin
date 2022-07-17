package com.example.movcompbmcr2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GRecyclerView : AppCompatActivity() {

    var totalLikes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grecycler_view)
        val listaEntrador = arrayListOf<BEntrenador>()
        listaEntrador.add(BEntrenador(1,"Brandon", "Campoverde"))
        listaEntrador.add(BEntrenador(2,"Martin", "Rodriguez"))

        val recyclerView = findViewById<RecyclerView>(R.id.rv_entrenadores)
        inicializarRV(listaEntrador,recyclerView)
    }

    fun inicializarRV(
        lista:ArrayList<BEntrenador>,
        recyclerView:RecyclerView
    ){
        val adaptador = FRecypeViewAdaptadorNombreCedula(
            this,
            lista,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    fun aumentarTotalLikes(){
        totalLikes= totalLikes+1
        val totaLikesTextView = findViewById<TextView>(R.id.tv_total_likes)
        totaLikesTextView.text = totalLikes.toString()
    }
}