package com.example.tvschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvschedule.data.EpisodeRepository
import com.example.tvschedule.data.database.EpisodeLocalRepository
import com.example.tvschedule.data.network.ScheduleLoader

class MainViewModelFactory(private val repository: EpisodeRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            val constructor = modelClass.getConstructor(EpisodeRepository::class.java)
            constructor.newInstance(repository)
        } catch (e: NoSuchMethodException) {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}