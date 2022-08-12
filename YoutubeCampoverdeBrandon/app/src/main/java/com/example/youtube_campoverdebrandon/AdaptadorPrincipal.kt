package com.example.youtube_campoverdebrandon

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

class AdaptadorPrincipal(val videosYT:List<VideoYTPirata>):RecyclerView.Adapter<ViewHolderPrincipal>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPrincipal {
        val layoutInfalter = LayoutInflater.from(parent.context)
        return ViewHolderPrincipal(layoutInfalter.inflate(R.layout.item_videoyt,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolderPrincipal, position: Int) {
        val item = videosYT[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return videosYT.size
    }
}