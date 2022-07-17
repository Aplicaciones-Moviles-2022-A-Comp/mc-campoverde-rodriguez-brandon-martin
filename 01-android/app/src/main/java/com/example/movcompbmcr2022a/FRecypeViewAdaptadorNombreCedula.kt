package com.example.movcompbmcr2022a

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecypeViewAdaptadorNombreCedula(
    private val contexto: GRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
    ):RecyclerView.Adapter<FRecypeViewAdaptadorNombreCedula.MyViewHolder>() {
    inner class MyViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val nombreTextView: TextView
        val cedulaTextView: TextView
        val likrdTextView: TextView
        val accionButton: Button
        var numeroLikers=0
        init {
            nombreTextView = view.findViewById(R.id.tv_nombre)
            cedulaTextView = view.findViewById(R.id.tv_cedula)
            likrdTextView= view.findViewById(R.id.tv_likes)
            accionButton = view.findViewById(R.id.btn_dar_like)
            accionButton.setOnClickListener{
                anadirLike()
            }
        }
        fun anadirLike(){
            numeroLikers = numeroLikers+1
            likrdTextView.text = numeroLikers.toString()
            contexto.aumentarTotalLikes()//Donde mi papa :,(
        }

    }

    //Setear el alyout que se va a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista,
                parent,
                false
            )
        return  MyViewHolder(itemView)
    }

    // setar ñps datps àra ña oteracion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenadorActual = this.lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.cedulaTextView.text = entrenadorActual.descripcion
        holder.accionButton.text = "Like ${entrenadorActual.nombre}"
        holder.likrdTextView.text = "0"
    }

    // tamano arreglo
    override fun getItemCount(): Int {
        return  this.lista.size
    }

}