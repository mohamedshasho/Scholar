package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scholar.center.R
import com.scholar.center.databinding.TeacherProfileItemBinding
import com.scholar.center.unit.Constants
import com.scholar.domain.model.TeacherWithSubjects


class TeacherProfileAdapter(
    diffCallback: DiffUtil.ItemCallback<TeacherWithSubjects>,
    private val onClick: (Int, ImageView) -> Unit,
) :
    PagingDataAdapter<TeacherWithSubjects, TeacherProfileAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TeacherProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.itemView.setOnClickListener {
//                val transitionName = ViewCompat.getTransitionName(holder.binding.teacherItemProfile)
                onClick(item.teacher.id, holder.binding.teacherItemProfile)

            }
            holder.binding.teacherProfileNameText.text = item.teacher.fullName

            holder.binding.teacherProfileSkillsText.text =
                item.subjects.joinToString(", ") { it.name }
            item.teacher.image?.let {image->
                Glide.with(holder.itemView.context)
                    .load("${Constants.BASE_URL}${image}")
                    .placeholder(R.drawable.ic_person)
                    .into(holder.binding.teacherItemProfile)
            }
        }
    }

    class ViewHolder(val binding: TeacherProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}


object TeacherComparator : DiffUtil.ItemCallback<TeacherWithSubjects>() {
    override fun areItemsTheSame(
        oldItem: TeacherWithSubjects,
        newItem: TeacherWithSubjects,
    ): Boolean {
        // Id is unique.
        return oldItem.teacher == newItem.teacher
    }

    override fun areContentsTheSame(
        oldItem: TeacherWithSubjects,
        newItem: TeacherWithSubjects,
    ): Boolean {
        return oldItem.teacher.id == newItem.teacher.id
    }
}