package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.MaterialReviewItemBinding
import com.scholar.domain.model.Review

class MaterialReviewAdapter(
    private var items: List<Review> = emptyList(),
) : RecyclerView.Adapter<MaterialReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MaterialReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setMaterialReviewsList(newItems: List<Review>) {
        items = newItems
        notifyItemChanged(0, newItems.size)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.materialReviewRatingBar.rating = item.rating
        holder.binding.materialReviewStudentComment.text = item.comment

    }

    class ViewHolder(val binding: MaterialReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}