package com.example.tvschedule.data.network

import com.example.tvschedule.data.models.Episode
import retrofit2.http.GET

interface ScheduleAPI {
    @GET("schedule")
    suspend fun fetchSchedule(): List<Episode>
}