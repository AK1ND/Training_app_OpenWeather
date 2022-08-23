package com.alex_kind.openweathermvvm.db

import androidx.lifecycle.LiveData
import com.alex_kind.openweathermvvm.models.db_weather.WeatherData


class WeatherRepository(private val weatherDao: WeatherDao) {

    val readAllData: LiveData<List<WeatherData>> = weatherDao.readAllWeatherData()

    suspend fun addWeatherData(weatherData: WeatherData){
        weatherDao.addWeatherData(weatherData)
    }

    suspend fun updateWeatherData(weatherData: WeatherData){
        weatherDao.updateWeatherData(weatherData)
    }

}