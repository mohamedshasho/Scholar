package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.StoryItemBinding
import com.scholar.data.model.CategoryLocal

class StoryAdapter(private val items: List<CategoryLocal>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

    }
    class ViewHolder(val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}