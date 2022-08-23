package com.alex_kind.openweathermvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alex_kind.openweathermvvm.models.db_weather.WeatherData


@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWeatherData(weatherData: WeatherData)

    @Query("SELECT * FROM weather_table ORDER BY id ASC")
    fun readAllWeatherData(): LiveData<List<WeatherData>>

    @Update
    suspend fun updateWeatherData(weatherData: WeatherData)

}