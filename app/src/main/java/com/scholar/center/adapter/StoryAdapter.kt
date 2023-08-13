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
import com.scholar.domain.model.StoryWithStudent

class StoryAdapter(
    diffCallback: DiffUtil.ItemCallback<StoryWithStudent>,
    private val onClick: (Int) -> Unit,
) :
    PagingDataAdapter<StoryWithStudent, StoryAdapter.ViewHolder>(diffCallback) {

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

            holder.itemView.setOnClickListener { onClick(item.story.id) }

            holder.binding.storyItemNameText.text = item.story.title
            holder.binding.storyItemDescriptionText.text = item.story.description
            holder.binding.storyItemDateText.text = item.story.datePublication
            holder.binding.storyItemStudentName.text = item.student.fullName
            item.story.image?.let { cover ->
                Glide.with(holder.itemView.context).load("${BASE_URL}${cover}")
                    .placeholder(R.drawable.story_placeholder)
                    .error(R.drawable.story_placeholder)
                    .into(holder.binding.storyItemCover)
            }
            item.student.image?.let { image ->
                Glide.with(holder.itemView.context).load("${BASE_URL}${image}")
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(holder.binding.storyItemStudentImage)
            }
        }
    }

    class ViewHolder(val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

object StoryComparator : DiffUtil.ItemCallback<StoryWithStudent>() {
    override fun areItemsTheSame(oldItem: StoryWithStudent, newItem: StoryWithStudent): Boolean {
        // Id is unique.
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: StoryWithStudent, newItem: StoryWithStudent): Boolean {
        return oldItem.story.id == newItem.story.id
    }
}