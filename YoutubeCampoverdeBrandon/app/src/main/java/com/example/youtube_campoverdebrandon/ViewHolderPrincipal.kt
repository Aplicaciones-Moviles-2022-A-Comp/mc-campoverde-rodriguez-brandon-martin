package com.example.youtube_campoverdebrandon

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class ViewHolderPrincipal(view:View):RecyclerView.ViewHolder(view) {

    val imgVideo = view.findViewById<ImageView>(R.id.imgvw_imagen_video)
    val imgCanal = view.findViewById<CircleImageView>(R.id.imgvw_imagen_canal)
    val tvTitulo = view.findViewById<TextView>(R.id.tv_titulo_video)
    val tvCanal = view.findViewById<TextView>(R.id.tv_nombre_canal)
    fun render(video:VideoYTPirata){
        tvTitulo.text = video.titulo
        tvCanal.text = video.canal
        Glide.with(imgVideo.context).load(video.imagen).into(imgVideo)
        Glide.with(imgCanal.context).load(video.imagenCanal).into(imgCanal)
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