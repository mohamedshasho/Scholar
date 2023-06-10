package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.CardViewClassItemBinding
import com.scholar.domain.model.Category

class CardClassITemAdapter(
    private val items: List<Category> = emptyList(),
    private val onClick: (Int) -> Unit,
) :
    RecyclerView.Adapter<CardClassITemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardViewClassItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.classItemButton.text = item.name
        holder.binding.classItemButton.setOnClickListener {
            onClick(item.id)
        }

    }

    class ViewHolder(val binding: CardViewClassItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}