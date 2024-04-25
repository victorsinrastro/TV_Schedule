package com.example.tvschedule.ui.main.adapters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.tvschedule.databinding.ItemScheduleListBinding
import com.example.tvschedule.data.models.ChannelSchedule
import com.example.tvschedule.data.models.Episode
import com.example.tvschedule.ui.details.DetailsActivity

const val EPISODE = "Episode"

class ChannelScheduleViewHolder(private val binding: ItemScheduleListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(channelSchedule: ChannelSchedule) {
        binding.apply {
            this.channelSchedule = channelSchedule

            val episodes = channelSchedule.episodes
            binding.currentEpisode.setOnClickListener {
                launchDetailsActivity(binding.root.context, episodes, 0)
            }
            binding.nextEpisode.setOnClickListener {
                launchDetailsActivity(binding.root.context, episodes, 1)
            }
            binding.subsequentEpisode.setOnClickListener {
                launchDetailsActivity(binding.root.context, episodes, 2)
            }
        }
        binding.executePendingBindings()
    }

    private fun launchDetailsActivity(context: Context, episodes: List<Episode>, index: Int) {
        if (index < episodes.size) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EPISODE,episodes[index].id)
            context.startActivity(intent)
        }
    }
}
