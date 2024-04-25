package com.example.tvschedule.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvschedule.R
import com.example.tvschedule.databinding.ItemButtonHourBinding
import com.example.tvschedule.ui.main.MainViewModel

class HourButtonAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<HourButtonAdapter.HourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemButtonHourBinding.inflate(inflater, parent, false)
        return HourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hour = position + 1
        holder.bind(hour)
    }

    override fun getItemCount() = 23

    inner class HourViewHolder(private val binding: ItemButtonHourBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hour: Int) {
            val hourText = itemView.context.getString(R.string.hour_format, hour)
            binding.btnHour.text = hourText
            binding.btnHour.setOnClickListener {
                viewModel.updateScheduleHour(hour)
            }
        }
    }
}