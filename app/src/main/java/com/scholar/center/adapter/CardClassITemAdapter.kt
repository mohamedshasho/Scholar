package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.CardViewClassItemBinding
import com.scholar.domain.model.ClassRoom
import com.scholar.domain.model.Stage

class CardClassITemAdapter<T>(
    private var items: List<T> = emptyList(),
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
        with(holder.binding) {
            when (val item = items[position]) {
                is Stage -> {
                    classItemButton.text = item.name
                    classItemButton.setOnClickListener {
                        onClick(item.id)
                    }
                }
                is ClassRoom -> {
                    classItemButton.text = item.name
                    classItemButton.setOnClickListener {
                        onClick(item.id)
                    }
                }
            }
        }
    }

    fun setData(l: List<T>) {
        val lastItemSize = items.size
        items = l
        notifyItemChanged(lastItemSize, l.size)
    }

    class ViewHolder(val binding: CardViewClassItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}