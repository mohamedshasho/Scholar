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
            with(holder.binding) {
                materialSubjectText.text = item.material.title
                val context = holder.itemView.context
                if (item.teacher?.name != null) {
                    materialSubjectTeacherText.text = item.teacher?.name
                } else {
                    materialSubjectTeacherText.visibility = View.GONE
                    materialSubjectTeacherImage.visibility = View.GONE
                }
                materialSubjectNumberHoursText.text =
                    context.getString(R.string.hour, item.material.hoursNumberOfWeek)

                val price = item.material.price
                val discount = item.material.discount

                materialSubjectPriceText.visibility =
                    if (price == null || price == 0) View.GONE else View.VISIBLE
                materialSubjectDiscountText.visibility =
                    if (discount == null || discount == 0) View.GONE else View.VISIBLE

                if (price != null && price != 0) {
                    if (discount != null && discount != 0) {
                        materialSubjectDiscountText.text =
                            context.getString(R.string.price_only, price)
                        materialSubjectDiscountText.paintFlags =
                            materialSubjectDiscountText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                        val discountedPrice = price.applyDiscount(discount)
                        materialSubjectPriceText.text =
                            context.getString(R.string.syr, discountedPrice)
                    } else {
                        materialSubjectPriceText.text = context.getString(R.string.syr, price)
                    }
                }

                materialSubjectStarText.text =
                    if (item.totalRate.isNaN()) "0" else item.totalRate.toString()

                item.teacher?.image?.let { image ->
                    Glide.with(holder.itemView)
                        .load("${Constants.BASE_URL}${image}")
                        .into(materialSubjectTeacherImage)
                }
                root.setOnClickListener {
                    navigateToDetails(item.material.id)
                }
                materialSubjectTeacherText.setOnClickListener {
                    item.teacher?.teacherId?.let { navigateToTeacher?.invoke(it) }
                }
                materialSubjectTeacherImage.setOnClickListener {
                    item.teacher?.teacherId?.let { navigateToTeacher?.invoke(it) }
                }
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