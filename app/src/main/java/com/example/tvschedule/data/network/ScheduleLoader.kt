package com.example.tvschedule.data.network

import com.example.tvschedule.data.models.Episode
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val BASE_URL = "https://api.tvmaze.com/"

class ScheduleLoader : ScheduleAPI {
    private val scheduleAPI: ScheduleAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScheduleAPI::class.java)
    }


    override suspend fun fetchSchedule(): List<Episode> = try {
        scheduleAPI.fetchSchedule()
    } catch (e: Exception) {
        throw NetworkException("Failed to fetch episode list", e)
    } catch (e: IOException) {
        throw NetworkException("Network error occurred", e)
    }

    class NetworkException(message: String, cause: Throwable) : Exception(message, cause)

}