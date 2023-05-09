package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.MaterialSubjectItemBinding
import com.scholar.data.model.MaterialSubject

class MaterialSubjectAdapter(private val items: List<MaterialSubject>) :
    RecyclerView.Adapter<MaterialSubjectAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MaterialSubjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.materialSubjectText.text = item.name

    }

    class ViewHolder(val binding: MaterialSubjectItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}