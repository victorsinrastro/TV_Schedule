package com.example.tvschedule.data.database

import androidx.room.TypeConverter
import com.example.tvschedule.data.models.Show
import com.google.gson.Gson

class Converters {
    private val showGson = Gson()

    @TypeConverter
    fun fromShowJson(json: String?): Show? {
        return showGson.fromJson(json, Show::class.java)
    }

    @TypeConverter
    fun toShowJson(show: Show?): String? {
        return showGson.toJson(show)
    }
}