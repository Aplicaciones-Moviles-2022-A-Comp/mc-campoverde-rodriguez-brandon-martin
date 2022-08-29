package com.example.proyecto_campoverdebrandon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorSpinner(
    context: Context,
    categoryList: ArrayList<Categoria>
):ArrayAdapter<Categoria>(context,0,categoryList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    fun initView(position: Int, convertView: View?, parent: ViewGroup):View{
        val categoria = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner,parent,false)
        val icono = view.findViewById<ImageView>(R.id.imgv_spinner)
        val texto = view.findViewById<TextView>(R.id.tv_item_spinner)
        icono.setImageResource(categoria!!.icono)
        texto.text= categoria.categoria

        return view
    }
}