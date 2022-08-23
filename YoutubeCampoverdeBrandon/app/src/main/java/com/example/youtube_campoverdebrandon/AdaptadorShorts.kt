package com.example.youtube_campoverdebrandon

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class AdaptadorShorts (
    private val contexto: Shorts,
    private val lista: ArrayList<ShortsPiratas>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AdaptadorShorts.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val titulovideoTextView: TextView
        val titulocanalTextView: TextView
        val imagenvideoImageView: ImageView
        val imagencanalCircleImageView: CircleImageView
        init {
            titulovideoTextView = view.findViewById(R.id.tv_titulo_video_short)
            titulocanalTextView = view.findViewById(R.id.tv_nombre_canal_short)
            imagenvideoImageView= view.findViewById(R.id.imv_shorts)
            imagencanalCircleImageView = view.findViewById(R.id.imgvw_imagen_canal_short)

        }
        fun render(video:ShortsPiratas){
            itemView.setOnClickListener {
                irActividad(VideoPrincipalActivity::class.java,video.url)
            }
        }
        fun irActividad(
            clase:Class<*>,
            url:String
        ){
            var contexto = itemView.context
            val intent = Intent(contexto, clase)
            intent.putExtra("url",url)
            contexto.startActivity(intent)
        }
    }

    //Setear el alyout que se va a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_short,
                parent,
                false
            )
        return  MyViewHolder(itemView)
    }

    // setar datos para operacion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val shortActual = this.lista[position]
        holder.titulovideoTextView.text = shortActual.titulo
        holder.titulocanalTextView.text = shortActual.canal
        Glide.with(holder.imagenvideoImageView.context).load(shortActual.imagen).into(holder.imagenvideoImageView)
        Glide.with(holder.imagencanalCircleImageView.context).load(shortActual.imagenCanal).into(holder.imagencanalCircleImageView)
        holder.render(shortActual)
    }

    // tamano arreglo
    override fun getItemCount(): Int {
        return  this.lista.size
    }

}