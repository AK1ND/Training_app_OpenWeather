package com.alex_kind.openweathermvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex_kind.openweathermvvm.models.current_weather.MainModelCurrentWeather
import com.alex_kind.openweathermvvm.models.forecast.MainModelForecast

class FragmentsViewModel : ViewModel() {


    //data set from activity
    private val _currentWeatherData = MutableLiveData<MainModelCurrentWeather>()
    val currentWeatherData: LiveData<MainModelCurrentWeather> = _currentWeatherData

    private val _forecastWeatherData = MutableLiveData<MainModelForecast>()
    val forecastWeatherData: LiveData<MainModelForecast> = _forecastWeatherData
    //end

    val errorLoading = MutableLiveData<Boolean>()

    fun setDataCurrentWeather(data: MainModelCurrentWeather) {
        _currentWeatherData.value = data
    }

    fun setDataForecast(data: MainModelForecast) {
        _forecastWeatherData.value = data
    }

    fun setErrorBool(bool: Boolean){
        errorLoading.value = bool
    }


}