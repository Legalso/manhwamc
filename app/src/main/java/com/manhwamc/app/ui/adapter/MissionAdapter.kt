package com.manhwamc.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manhwamc.app.data.model.Mission
import com.manhwamc.app.databinding.ItemMissionBinding

class MissionAdapter(
    private val onCompleteClick: (Mission) -> Unit
) : ListAdapter<Mission, MissionAdapter.MissionViewHolder>(MissionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder {
        val binding = ItemMissionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MissionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MissionViewHolder(
        private val binding: ItemMissionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mission: Mission) {
            binding.apply {
                missionTitle.text = mission.title
                missionXp.text = "${mission.xp} XP"
                completeButton.setOnClickListener {
                    onCompleteClick(mission)
                }
            }
        }
    }

    private class MissionDiffCallback : DiffUtil.ItemCallback<Mission>() {
        override fun areItemsTheSame(oldItem: Mission, newItem: Mission): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mission, newItem: Mission): Boolean {
            return oldItem == newItem
        }
    }
}