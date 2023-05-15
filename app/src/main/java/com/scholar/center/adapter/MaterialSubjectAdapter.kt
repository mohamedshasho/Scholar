package com.scholar.center.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scholar.center.databinding.MaterialSubjectItemBinding
import com.scholar.data.model.CategoryLocal
import com.google.android.material.R.attr as theme

class MaterialSubjectAdapter(
    private val items: List<CategoryLocal>,
    private val onClick: (position: Int) -> Unit,
) :
    RecyclerView.Adapter<MaterialSubjectAdapter.ViewHolder>() {

    private var itemSelected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MaterialSubjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            onClick(position)
        }
        if (itemSelected == position) {
            val selectedColor = getThemeColor(
                holder.itemView.context,
                theme.colorSecondaryVariant
            )
            holder.binding.materialSubjectCard.setCardBackgroundColor(selectedColor)
        } else {
            val unSelectedColor = getThemeColor(
                holder.itemView.context,
                theme.colorPrimaryContainer
            )
            holder.binding.materialSubjectCard.setCardBackgroundColor(unSelectedColor)
        }
        holder.binding.materialSubjectText.text = item.name

    }

    private fun getThemeColor(context: Context, resId: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            resId,
            typedValue,
            true
        )
        return typedValue.data
    }

    fun setItemSelected(position: Int) {
        val previousSelected = itemSelected
        itemSelected = position
        notifyItemChanged(itemSelected)
        notifyItemChanged(previousSelected)
    }

    class ViewHolder(val binding: MaterialSubjectItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
