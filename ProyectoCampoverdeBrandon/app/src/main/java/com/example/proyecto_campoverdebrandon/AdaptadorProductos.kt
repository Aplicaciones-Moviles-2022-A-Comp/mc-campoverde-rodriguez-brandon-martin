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

class AdaptadorProductos (
    private val contexto: Productos,
    private val lista: ArrayList<DataProductos>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AdaptadorProductos.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nombreproductoTextView: TextView
        val descripcionproductoTextView: TextView
        val stockproductoTextView: TextView
        val precioproductoTextView: TextView
        val imagenproductoImageView: ImageView
        val buttonComprar: Button
        var numprod:Long = 0

        init {
            nombreproductoTextView = view.findViewById(R.id.tv_nombre_producto)
            descripcionproductoTextView = view.findViewById(R.id.tv_descripcion_producto)
            stockproductoTextView = view.findViewById(R.id.tv_cantidad_producto)
            precioproductoTextView = view.findViewById(R.id.tv_precio_producto)
            imagenproductoImageView = view.findViewById(R.id.imgvw_imagen_producto)
            buttonComprar = view.findViewById(R.id.btn_aniadir_carrito)
            buttonComprar.setOnClickListener {
                aniadircarrito()
            }
        }
        fun aniadircarrito(){
            numprod=numprod+1
            contexto.agregaralcarrito(nombreproductoTextView.text.toString(),numprod,precioproductoTextView.text.toString().toDouble())
        }

    }

    //Setear el alyout que se va a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_producto,
                parent,
                false
            )
        return  MyViewHolder(itemView)
    }

    // setar datos para operacion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val productoActual = this.lista[position]
        holder.nombreproductoTextView.text = productoActual.nombre
        holder.descripcionproductoTextView.text = productoActual.descripcion
        holder.precioproductoTextView.text = productoActual.precio.toString()
        holder.stockproductoTextView.text = productoActual.stock.toString()
        Glide.with(holder.imagenproductoImageView.context).load(productoActual.imagen).into(holder.imagenproductoImageView)
    }

    // tamano arreglo
    override fun getItemCount(): Int {
        return  this.lista.size
    }

}