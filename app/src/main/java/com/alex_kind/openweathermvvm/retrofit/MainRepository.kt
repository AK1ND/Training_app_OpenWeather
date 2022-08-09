package com.alex_kind.openweathermvvm.retrofit

class MainRepository(
    private val retrofitService: RetrofitService,
) {

    suspend fun getCityName(lat: String?, lon: String?) = retrofitService.getCityName(lat, lon)

    suspend fun getWeather(cityName: String) = retrofitService.getWeather(cityName)
}