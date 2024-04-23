package com.example.tvschedule.network

import com.example.tvschedule.network.models.ScheduleResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TVScheduleLoader : TVScheduleAPI {
    private val tvScheduleAPI: TVScheduleAPI

    companion object {
        private const val BASE_URL = "https://api.tvmaze.com"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        tvScheduleAPI = retrofit.create(TVScheduleAPI::class.java)
    }

    override fun fetchSchedule(): Call<ScheduleResponse> {
        return tvScheduleAPI.fetchSchedule()
    }
}