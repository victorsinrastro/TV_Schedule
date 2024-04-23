package com.example.tvschedule.network.models

open class ScheduleResponse {
    private val _episodeList: MutableList<Episode> = mutableListOf()

    val episodeList: List<Episode>
        get() = _episodeList.toList()
    data class Episode(
        val id: String,
        val url: String,
        val name: String,
        val airDate: String,
        val airTime: String
    )
}