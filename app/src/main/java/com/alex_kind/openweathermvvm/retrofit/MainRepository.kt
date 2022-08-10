package com.alex_kind.openweathermvvm.retrofit

class MainRepository(
    private val retrofitService: RetrofitService,
) {

    suspend fun getCityName(lat: String?, lon: String?) = retrofitService.getCityName(lat, lon)

    suspend fun getForecast(lat: String?, lon: String?) = retrofitService.getForecast(lat, lon)

    suspend fun getCurrentWeather(lat: String?, lon: String?) = retrofitService.getCurrentWeather(lat, lon)
}