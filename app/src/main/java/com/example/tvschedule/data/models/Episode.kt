package com.example.tvschedule.data.models

import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey val id: Int,
    val name: String?,
    @SerializedName("airtime") val airTime: String?,
    val show: Show?
)

data class Show(
    val id: String,
    val url: String,
    val name: String,
    val type: String,
    val language: String,
    val genres: List<String>,
    val status: String,
    val runtime: Int,
    val averageRuntime: Int,
    val premiered: String,
    val ended: String?,
    val officialSite: String?,
    val schedule: Schedule,
    val network: Network?,
    val webChannel: WebChannel?,
    val image: Image?,
    val summary: String,
    val updated: Long,
    val links: Links
)

data class Schedule(
    val time: String,
    val days: List<String>
)

data class Network(
    val id: String,
    val name: String,
    val country: Country,
    val officialSite: String?
)
data class Country(
    val name: String,
    val code: String,
    val timezone: String
)

data class Links(
    val self: Self,
    val show: ShowLink
)

data class Self(
    val href: String
)

data class ShowLink(
    val href: String,
    val name: String
)

data class WebChannel(
    val id: String,
    val name: String,
    val country: Country,
    val officialSite: String?
)

data class Image(
    val medium: String,
    val original: String
)
