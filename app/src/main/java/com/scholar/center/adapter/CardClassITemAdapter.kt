package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.CardViewClassItemBinding
import com.scholar.domain.model.Category
import com.scholar.domain.model.ClassMate
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

        when(val item = items[position]){
            is Stage->{
                holder.binding.classItemButton.text = item.name
                holder.binding.classItemButton.setOnClickListener {
                    onClick(item.id)
                }
            }
            is ClassMate->{
                holder.binding.classItemButton.text = item.name
                holder.binding.classItemButton.setOnClickListener {
                    onClick(item.id)
                }
            }
            else->{}
        }
    }


    fun setData(l: List<T>){
        val lastItemSize = items.size
        items = l
        notifyItemChanged(lastItemSize, l.size)
    }
    class ViewHolder(val binding: CardViewClassItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}