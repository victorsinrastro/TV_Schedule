package com.example.tvschedule.data.database

import com.example.tvschedule.data.models.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodeLocalRepository(private val episodeDao: EpisodeDao) {

    suspend fun insertEpisodes(episodes: List<Episode>) {
        withContext(Dispatchers.IO) {
            val episodeEntities = episodes.map { episode ->
                Episode(
                    id = episode.id,
                    name = episode.name,
                    airTime = episode.airTime,
                    show = episode.show
                )
            }
            episodeDao.insertEpisodes(episodeEntities)
        }
    }

    suspend fun getAllEpisodes(): List<Episode> {
        return withContext(Dispatchers.IO) {
            episodeDao.getAllEpisodes()
        }
    }

    suspend fun deleteAllEpisodes() {
        withContext(Dispatchers.IO) {
            episodeDao.deleteAllEpisodes()
        }
    }

    suspend fun getEpisodeById(episodeId: Int): Episode? {
        return withContext(Dispatchers.IO) {
            episodeDao.getEpisodeById(episodeId)
        }
    }
}