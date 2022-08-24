package com.alex_kind.openweathermvvm.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.alex_kind.openweathermvvm.const.ROOM_DB_DATA
import com.alex_kind.openweathermvvm.db.WeatherDatabase
import com.alex_kind.openweathermvvm.db.WeatherRepository
import com.alex_kind.openweathermvvm.models.db_weather.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {


    val readAllWeatherData: LiveData<List<WeatherData>>
    private val repository: WeatherRepository

    init {
        val weatherDao = WeatherDatabase.getDatabase(application).weatherDao()
        repository = WeatherRepository(weatherDao)
        readAllWeatherData = repository.readAllData
    }

    fun addWeatherData(weatherData: WeatherData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWeatherData(weatherData)
            Log.d(ROOM_DB_DATA, "ROOM ADD")
        }
    }

    fun updateWeatherData(weatherData: WeatherData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWeatherData(weatherData)
            Log.d(ROOM_DB_DATA, "ROOM UPDATE")
        }
    }

}