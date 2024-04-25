package com.example.tvschedule.data

import com.example.tvschedule.data.database.EpisodeLocalRepository
import com.example.tvschedule.data.network.ScheduleLoader
import com.example.tvschedule.data.models.Episode

class EpisodeRepository(
    private val episodeLocalRepository: EpisodeLocalRepository,
    private val scheduleLoader: ScheduleLoader
) {
    suspend fun getEpisodes(): List<Episode> {

        val localEpisodes = episodeLocalRepository.getAllEpisodes()
        if (localEpisodes.isNotEmpty()) {
            return localEpisodes
        }

        val episodes = scheduleLoader.fetchSchedule()
        insertEpisodes(episodes)

        return episodes
    }

    private suspend fun insertEpisodes(episodes: List<Episode>) {
        episodeLocalRepository.insertEpisodes(episodes)
    }

    suspend fun getEpisodeById(episodeId: Int): Episode? {
        return episodeLocalRepository.getEpisodeById(episodeId)
    }

}