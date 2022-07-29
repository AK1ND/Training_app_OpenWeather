package com.alex_kind.openweathermvvm.models.weather

data class MainModelWeather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Model>,
    val message: Int
)