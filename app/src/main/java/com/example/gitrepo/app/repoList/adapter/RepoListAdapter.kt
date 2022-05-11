package com.example.gitrepo.app.repoList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.gitrepo.R
import com.example.gitrepo.domain.model.Repo

class RepoListAdapter(
    private val context:Context,
    private val repoList:ArrayList<Repo>
) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    var onItemClick : ((Int)->Unit)? = null

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val name:TextView = v.findViewById(R.id.repo_name)
        val cl:ConstraintLayout = v.findViewById(R.id.custom_repo_list_item)
        val icon:ImageView = v.findViewById(R.id.repo_icon)

        init {
            cl.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
            name.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
            icon.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.custom_repo_list_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: RepoListAdapter.ViewHolder, position: Int) {
        holder.name.text = repoList[position].name
        Glide.with(context)
            .load(repoList[position].icon)
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
            .transition(
                DrawableTransitionOptions.withCrossFade()
                    .crossFade())
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }
}