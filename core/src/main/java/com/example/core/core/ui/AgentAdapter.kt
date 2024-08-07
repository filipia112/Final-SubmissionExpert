package com.example.core.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.core.domain.model.Agent
import com.example.core.databinding.ItemListAgentsBinding

class AgentAdapter : RecyclerView.Adapter<AgentAdapter.ListViewHolder>() {
    private var listData = ArrayList<Agent>()
    var onItemClick: ((Agent, View) -> Unit)? = null

    fun setData(newListData: List<Agent>?) {
        if (newListData == null) return
        val diffCallback = AgentDiffCallback(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListAgentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ItemListAgentsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Agent) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.background)
                    .into(IvbackgroundTextName)
                Glide.with(itemView.context)
                    .load(data.fullPortrait)
                    .into(IvagentsImage)
                tvNameAgents.text = data.displayName
                itemView.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClick?.invoke(data, IvagentsImage)
                    }
                }
            }
        }
    }
}

class AgentDiffCallback(
    private val oldList: List<Agent>,
    private val newList: List<Agent>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].uuid == newList[newItemPosition].uuid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}