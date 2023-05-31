package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.SubjectItemBinding
import com.scholar.data.model.CategoryLocal

class SubjectAdapter(
    private val items: List<CategoryLocal>,
    private val navigateToTeacher: (Int) -> Unit,
) :
    RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SubjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.materialSubjectText.text = item.name

        holder.binding.materialSubjectTeacherText.setOnClickListener {
            navigateToTeacher.invoke(1)
        }
        holder.binding.materialSubjectTeacherImage.setOnClickListener {
            navigateToTeacher.invoke(1)
        }

    }

    class ViewHolder(val binding: SubjectItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}