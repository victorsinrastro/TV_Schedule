package com.example.tvschedule.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvschedule.data.EpisodeRepository
import com.example.tvschedule.data.models.ChannelSchedule
import com.example.tvschedule.data.models.Episode
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar

class MainViewModel(private val repository: EpisodeRepository) : ViewModel() {
    private val _schedule = MutableLiveData<List<ChannelSchedule>>()
    val schedule: LiveData<List<ChannelSchedule>> = _schedule

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _scheduleHour = MutableLiveData<Int>().apply {
        value = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }

    init {
        fetchSchedule()
    }

    private fun fetchSchedule() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val scheduleList = repository.getEpisodes()
                _schedule.value = processScheduleResponse(scheduleList)
            } catch (e: Exception) {
                handleNetworkError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun processScheduleResponse(scheduleResponseList: List<Episode>): List<ChannelSchedule> {
        val episodesByChannel = scheduleResponseList.groupBy { episode ->
            episode.show?.network?.name ?: episode.show?.webChannel?.name ?: "Unknown Channel"
        }.mapValues { (_, episodes) ->
            episodes.filter { episode ->
                val episodeHour = episode.airTime?.substringBefore(":")?.toIntOrNull() ?: -1
                episodeHour != -1 && episodeHour >= _scheduleHour.value!!
            }
        }.filterValues { episodes -> episodes.isNotEmpty() }

        val sortedChannelSchedules =  episodesByChannel.map { (channel, episodes) ->
            ChannelSchedule(channel, episodes.sortedBy { it.airTime })
        }
        return sortedChannelSchedules.sortedBy { it.channel}
    }

    private fun handleNetworkError(e: Exception) {
        when (e) {
            is HttpException -> _errorMessage.value = "Error fetching Episodes list: ${e.message()}"
            is IOException -> _errorMessage.value = "Network error. Please check your connection."
            else -> {
                _errorMessage.value =
                    "Something went wrong. Please try again later. ${e.localizedMessage}"
                Log.e("MainViewModel", "fetchSchedule: ${e.message}", e)
            }
        }
    }

    fun updateScheduleHour(hour:Int){
        _scheduleHour.value= hour
        fetchSchedule()
    }
}