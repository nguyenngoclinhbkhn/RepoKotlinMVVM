package com.example.repokotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repokotlinmvvm.R
import com.example.repokotlinmvvm.model.local.enity.Repo

class AdapterRepoFavo (listener: OnRepoListener) :
    RecyclerView.Adapter<AdapterRepoFavo.RepoHolder>() {
    private var inflater: LayoutInflater? = null
    private var list: List<Repo>
    private val listener: OnRepoListener
    fun setList(list: List<Repo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoHolder {
        inflater = LayoutInflater.from(parent.context)
        return RepoHolder(inflater!!.inflate(R.layout.item_repo, parent, false))
    }

    override fun onBindViewHolder(
        holder: RepoHolder,
        position: Int
    ) {
        val repo = list[position]
        holder.txtRepo?.setText(repo?.fullName)
        holder.relativeLayout.setOnClickListener { listener.OnRepoClicked(repo) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RepoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val relativeLayout: RelativeLayout = itemView.findViewById(R.id.relativeRepo)
        val txtRepo: TextView = itemView.findViewById(R.id.nameRepo)

    }

    interface OnRepoListener {
        fun OnRepoClicked(repo: Repo?)
    }

    init {
        list = ArrayList<Repo>()
        this.listener = listener
    }
}