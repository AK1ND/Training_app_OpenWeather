package com.alex_kind.openweathermvvm.models.db_weather

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "weather_table")
data class WeatherData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val date: String,
    val cityName: String,
    val description: String,
    val humidity: Int,
    val wind: Double,
    val temp: Double
): Parcelable