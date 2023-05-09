package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.TeacherProfileItemBinding
import com.scholar.data.model.MaterialSubject

class TeacherProfileAdapter(private val items: List<MaterialSubject>) :
    RecyclerView.Adapter<TeacherProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TeacherProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

    }
    class ViewHolder(val binding: TeacherProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}