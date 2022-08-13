package com.alex_kind.openweathermvvm.retrofit

import com.alex_kind.openweathermvvm.const.APP_ID
import com.alex_kind.openweathermvvm.const.BASE_URL
import com.alex_kind.openweathermvvm.const.UNITS_METRIC
import com.alex_kind.openweathermvvm.models.current_weather.MainModelCurrentWeather
import com.alex_kind.openweathermvvm.models.geo.MainModelGeo
import com.alex_kind.openweathermvvm.models.forecast.MainModelForecast
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {


    @GET("geo/1.0/reverse")
    suspend fun getCityName(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") app_id: String = APP_ID
    ): Response<List<MainModelGeo>>


    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("units") units: String? = UNITS_METRIC,
        @Query("appid")app_id: String = APP_ID
    ): Response<MainModelForecast>

//    https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    @GET ("data/2.5/weather")
    suspend fun getCurrentWeather(
    @Query("lat")lat: String?,
    @Query("lon") lon: String?,
    @Query("units") units: String? = UNITS_METRIC,
    @Query("appid") app_id: String = APP_ID
    ): Response<MainModelCurrentWeather>


    companion object {
        var retrofitService: RetrofitService? = null
        fun getRetrofit(): RetrofitService {
            if (retrofitService == null) {
                retrofitService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitService::class.java)

            }
            return retrofitService!!
        }
    }
}