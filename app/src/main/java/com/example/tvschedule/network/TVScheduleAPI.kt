package com.example.tvschedule.network

import com.example.tvschedule.network.models.ScheduleResponse
import retrofit2.Call
import retrofit2.http.GET

interface TVScheduleAPI {
    @GET("schedule")
    fun fetchSchedule(): Call<ScheduleResponse>
}