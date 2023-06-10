package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.SubjectItemBinding
import com.scholar.domain.model.Material

class MaterialAdapter(
    private var items: List<Material> = emptyList(),
    private val navigateToTeacher: (Int) -> Unit,
) :
    RecyclerView.Adapter<MaterialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SubjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.materialSubjectText.text = item.title
        item.price?.let { holder.binding.materialSubjectPriceText.text = it.toString() }

        holder.binding.materialSubjectTeacherText.setOnClickListener {
            navigateToTeacher.invoke(1)
        }
        holder.binding.materialSubjectTeacherImage.setOnClickListener {
            navigateToTeacher.invoke(1)
        }
    }

    fun setList(l: List<Material>) {
        val lastItemSize = items.size
        items = l
        notifyItemChanged(lastItemSize, l.size)
    }

    class ViewHolder(val binding: SubjectItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}