package com.alex_kind.openweathermvvm.models.forecast

data class MainModelWeather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Model>,
    val message: Int
)