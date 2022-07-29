package com.alex_kind.openweathermvvm.models.geo

data class MainModelGeo(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)