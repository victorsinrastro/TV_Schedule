package com.example.tvschedule.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvschedule.data.EpisodeRepository
import com.example.tvschedule.data.models.Episode
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: EpisodeRepository) : ViewModel() {
    private val _fetchedEpisode = MutableLiveData<Episode?>()
    val episode: LiveData<Episode?> = _fetchedEpisode

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getEpisodeById(episodeId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val fetchedEpisode = repository.getEpisodeById(episodeId)
                _fetchedEpisode.value = fetchedEpisode
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching episode: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}