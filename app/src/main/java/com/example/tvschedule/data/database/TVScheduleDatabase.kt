package com.example.tvschedule.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tvschedule.data.models.Episode

@Database(entities = [Episode::class], version = 1)
@TypeConverters(Converters::class)
abstract class TVScheduleDatabase : RoomDatabase() {
    abstract fun episodeDao(): EpisodeDao

    companion object {
        @Volatile
        private var INSTANCE: TVScheduleDatabase? = null

        fun getDatabase(context: Context): TVScheduleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TVScheduleDatabase::class.java,
                    "episode_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}