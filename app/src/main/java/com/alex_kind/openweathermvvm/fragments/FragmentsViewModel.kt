package com.alex_kind.openweathermvvm.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex_kind.openweathermvvm.models.current_weather.MainModelCurrentWeather
import com.alex_kind.openweathermvvm.models.forecast.MainModelForecast

class FragmentsViewModel: ViewModel() {

    private val _currentWeatherData = MutableLiveData<MainModelCurrentWeather>()
    val currentWeatherData: LiveData<MainModelCurrentWeather> get() = _currentWeatherData

    private val _forecastWeatherData = MutableLiveData<MainModelForecast>()
    val forecastWeatherData: LiveData<MainModelForecast> get() = _forecastWeatherData


    fun setDataCurrentWeather(data: MainModelCurrentWeather){
        _currentWeatherData.value = data
    }

    fun setDataForecast(data: MainModelForecast){
        _forecastWeatherData.value = data
    }


}