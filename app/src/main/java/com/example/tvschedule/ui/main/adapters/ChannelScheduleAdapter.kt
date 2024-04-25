package com.example.tvschedule.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.tvschedule.databinding.ItemScheduleListBinding
import com.example.tvschedule.data.models.ChannelSchedule

class ChannelScheduleAdapter(var schedule: List<ChannelSchedule>) :
    Adapter<ChannelScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScheduleListBinding.inflate(inflater, parent, false)
        return ChannelScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return schedule.size
    }

    override fun onBindViewHolder(holder: ChannelScheduleViewHolder, position: Int) {
        val channelSchedule = schedule[position]
        channelSchedule.let { holder.bind(it) }
    }
}