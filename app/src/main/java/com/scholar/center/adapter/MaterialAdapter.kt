package com.scholar.center.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scholar.center.R
import com.scholar.center.databinding.SubjectItemBinding
import com.scholar.center.unit.Constants.BASE_URL
import com.scholar.center.unit.applyDiscount
import com.scholar.domain.model.MaterialWithTeacher

class MaterialAdapter(
    private var items: List<MaterialWithTeacher> = emptyList(),
    private val navigateToTeacher: ((Int) -> Unit)? = null,
    private val navigateToDetails: (Int) -> Unit,
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
        with(holder.binding) {
            materialSubjectText.text = item.material.title
            val context = root.context
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

            materialSubjectStarText.text = item.totalRate.toString()

            item.teacher?.image?.let { image ->
                Glide.with(context)
                    .load("${BASE_URL}${image}")
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

    fun setList(l: List<MaterialWithTeacher>) {
        val lastItemSize = items.size
        items = l
        notifyItemChanged(lastItemSize, l.size)
    }

    class ViewHolder(val binding: SubjectItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}