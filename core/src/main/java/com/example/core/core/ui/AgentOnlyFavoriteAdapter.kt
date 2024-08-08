package com.example.core.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.core.domain.model.Agent
import com.example.core.databinding.ItemListFavoriteAgentBinding

class AgentOnlyFavoriteAdapter : RecyclerView.Adapter<AgentOnlyFavoriteAdapter.ListViewHolder>() {
    private var listData = ArrayList<Agent>()
    var onItemClick: ((Agent) -> Unit)? = null

    fun setData(newListData: List<Agent>?) {
        if (newListData == null) return
        val diffCallback = AgentDiffCallback(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListFavoriteAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ItemListFavoriteAgentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Agent) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.killfeedPortrait)
                    .into(IvImageAgents)
                tvNameAgents.text = data.displayName
                tvNameDevelopers.text = data.developerName
                itemView.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClick?.invoke(listData[position])
                    }
                }

            }
        }
    }
}
