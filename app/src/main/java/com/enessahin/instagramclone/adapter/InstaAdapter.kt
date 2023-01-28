package com.enessahin.instagramclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enessahin.instagramclone.databinding.RecyclerRowBinding
import com.enessahin.instagramclone.model.InstaPost
import com.squareup.picasso.Picasso

class InstaAdapter(val PostList: ArrayList<InstaPost>) : RecyclerView.Adapter<InstaAdapter.InstaHolder>() {

    class InstaHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstaHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstaHolder(binding)
    }

    override fun onBindViewHolder(holder: InstaHolder, position: Int) {
        holder.binding.rvCommentText.setText(PostList.get(position).comment)
        holder.binding.rvEmailText.setText(PostList.get(position).userEmail)
        Picasso.get().load(PostList.get(position).downloadUrl).into(holder.binding.rvPostImage)
    }

    override fun getItemCount(): Int {
        return PostList.size
    }
}