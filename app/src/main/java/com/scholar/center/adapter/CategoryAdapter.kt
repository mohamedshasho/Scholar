package com.scholar.center.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.R
import com.scholar.center.databinding.CategoryItemBinding
import com.scholar.center.unit.getThemeColor
import com.scholar.domain.model.Category
import com.google.android.material.R.attr as theme

class CategoryAdapter(
    private var items: List<Category> = emptyList(),
    private val onClick: ((position: Int) -> Unit)? = null,
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var itemSelected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            onClick?.invoke(position)
        }
        if (itemSelected == position) {
            val selectedColor = holder.itemView.context.getThemeColor(
                theme.colorSecondaryVariant
            )
            holder.binding.categoryItemCard.setCardBackgroundColor(selectedColor)
        } else {
            val unSelectedColor = holder.itemView.context.getThemeColor(
                theme.colorPrimaryContainer
            )
            holder.binding.categoryItemCard.setCardBackgroundColor(unSelectedColor)
        }
        holder.binding.categoryItemText.text = item.name
        holder.binding.categoryItemImage.setImageResource(
            when(item.image){
                1->{R.drawable.ic_book}
                2->{R.drawable.summary}
                3->{R.drawable.video}
                4->{R.drawable.ic_book}
                5->{R.drawable.exam}
                else ->R.drawable.ic_book
            }
        )


        /*
        Glide.with(holder.itemView.context).load("${item.image}")
            .placeholder(R.drawable.ic_book)
            .into(holder.binding.categoryItemImage)
        */
    }



    fun setItemSelected(position: Int) {
        val previousSelected = itemSelected
        itemSelected = position
        notifyItemChanged(itemSelected)
        notifyItemChanged(previousSelected)
    }

    fun setMaterialSubjectList(subjects: List<Category>) {
        items = subjects
        notifyItemChanged(0, subjects.size)
    }

    class ViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
