package com.scholar.center.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scholar.center.R
import com.scholar.center.databinding.SubjectItemBinding
import com.scholar.center.unit.Constants
import com.scholar.center.unit.applyDiscount
import com.scholar.domain.model.MaterialWithTeacher

class MaterialPagingAdapter(
    diffCallback: DiffUtil.ItemCallback<MaterialWithTeacher>,
    private val navigateToTeacher: ((Int) -> Unit)? = null,
    private val navigateToDetails: (Int) -> Unit,
) :
    PagingDataAdapter<MaterialWithTeacher, MaterialPagingAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SubjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {


            holder.binding.materialSubjectText.text = item.material.title
            val context = holder.itemView.context
            if (item.teacher.name != null) {
                holder.binding.materialSubjectTeacherText.text = item.teacher.name
            } else {
                holder.binding.materialSubjectTeacherText.visibility = View.GONE
                holder.binding.materialSubjectTeacherImage.visibility = View.GONE
            }

            holder.binding.materialSubjectNumberHoursText.text =
                context.getString(R.string.hour, item.material.hoursNumberOfWeek)

            if (item.material.price != null && item.material.price != 0) {
                if (item.material.discount != null && item.material.discount != 0) {
                    holder.binding.materialSubjectDiscountText.text = context.getString(
                        R.string.price_only, item.material.price
                    )
                    holder.binding.materialSubjectDiscountText.paintFlags =
                        holder.binding.materialSubjectDiscountText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    val discountedPrice =
                        item.material.price?.applyDiscount(item.material.discount ?: 0)

                    holder.binding.materialSubjectPriceText.text = context.getString(
                        R.string.syr, discountedPrice
                    )
                } else {
                    holder.binding.materialSubjectDiscountText.visibility = View.GONE
                    holder.binding.materialSubjectPriceText.text = context.getString(
                        R.string.syr, item.material.price
                    )
                }

            } else {
                holder.binding.materialSubjectPriceText.visibility = View.GONE
            }
            holder.binding.materialSubjectStarText.text = item.totalRate.toString()

            item.teacher.image?.let { image ->
                Glide.with(holder.itemView)
                    .load("${Constants.BASE_URL}${image}")
                    .into(holder.binding.materialSubjectTeacherImage)
            }

            holder.itemView.setOnClickListener {
                navigateToDetails(item.material.id)
            }
            holder.binding.materialSubjectTeacherText.setOnClickListener {
                item.teacher.teacherId?.let { navigateToTeacher?.invoke(it) }
            }
            holder.binding.materialSubjectTeacherImage.setOnClickListener {
                item.teacher.teacherId?.let { navigateToTeacher?.invoke(it) }
            }
        }
    }

    class ViewHolder(val binding: SubjectItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

object MaterialsComparator : DiffUtil.ItemCallback<MaterialWithTeacher>() {
    override fun areItemsTheSame(
        oldItem: MaterialWithTeacher,
        newItem: MaterialWithTeacher,
    ): Boolean {
        // Id is unique.
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MaterialWithTeacher,
        newItem: MaterialWithTeacher,
    ): Boolean {
        return oldItem.material.id == newItem.material.id
    }
}