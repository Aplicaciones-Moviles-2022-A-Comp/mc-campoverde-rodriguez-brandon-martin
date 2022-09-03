package com.example.proyecto_campoverdebrandon
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_campoverdebrandon.DataProductos
import com.example.proyecto_campoverdebrandon.Productos
import com.example.proyecto_campoverdebrandon.R

class AdaptadorCarrito (
    private val contexto: Carrito,
    private val lista: ArrayList<CarritoData>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AdaptadorCarrito.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nombreproductoTextView: TextView
        val cantidadproductoTextView: TextView
        val precioproductoTextView: TextView

        init {
            nombreproductoTextView = view.findViewById(R.id.tv_nombre_prod_carrito)
            cantidadproductoTextView = view.findViewById(R.id.tv_cantidad_prod_carrito)
            precioproductoTextView = view.findViewById(R.id.tv_total_pagar_item_carrito)
        }

    }

    //Setear el alyout que se va a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_carrito,
                parent,
                false
            )
        return  MyViewHolder(itemView)
    }

    // setar datos para operacion
    override fun onBindViewHolder(holder: AdaptadorCarrito.MyViewHolder, position: Int) {
        val productoActual = this.lista[position]
        holder.nombreproductoTextView.text = productoActual.nombre
        holder.cantidadproductoTextView.text = productoActual.cantidad.toString()
        holder.precioproductoTextView.text = (productoActual.precio*productoActual.cantidad).toString()
        contexto.calcularPrecioTotal(holder.precioproductoTextView.text.toString().toDouble())
    }
    // tamano arreglo
    override fun getItemCount(): Int {
        return  this.lista.size
    }



}