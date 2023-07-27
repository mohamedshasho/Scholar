package com.scholar.center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scholar.center.R
import com.scholar.center.databinding.StoryItemBinding
import com.scholar.center.unit.Constants.BASE_URL
import com.scholar.domain.model.Story

class StoryAdapter(
    diffCallback: DiffUtil.ItemCallback<Story>,
    private val onClick: (Int) -> Unit,
) :
    PagingDataAdapter<Story, StoryAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item can be null. ViewHolder must support binding a
        // null item as a placeholder.
        if (item != null) {

            holder.itemView.setOnClickListener { onClick(item.id) }

            holder.binding.storyItemNameText.text = item.title
            holder.binding.storyItemDescriptionText.text = item.description
            holder.binding.storyItemDateText.text = item.datePublication
            holder.binding.storyItemStudentName.text = item.studentName
            Glide.with(holder.itemView.context).load("${BASE_URL}${item.image}")
                .placeholder(R.drawable.ic_story)
                .into(holder.binding.storyItemCover)

            Glide.with(holder.itemView.context).load("${BASE_URL}${item.studentProfile}")
                .placeholder(R.drawable.ic_person)
                .into(holder.binding.storyItemStudentImage)
        }
    }

    class ViewHolder(val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

object StoryComparator : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        // Id is unique.
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.id == newItem.id
    }
}