package com.example.tvschedule.data.models

data class ChannelSchedule(
    val channel: String,
    val episodes: List<Episode>
)