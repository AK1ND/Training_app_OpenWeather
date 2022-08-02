package com.alex_kind.openweathermvvm.retrofit

import com.alex_kind.openweathermvvm.const.BASE_URL
import com.alex_kind.openweathermvvm.models.geo.MainModelGeo
import com.alex_kind.openweathermvvm.models.weather.MainModelWeather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("data/2.5/forecast?appid=6e298e72d16587b721abb30bbf7c721a")
    suspend fun getWeather(
        @Query("q") q: String
    ): Response<MainModelWeather>


    @GET("geo/1.0/reverse?appid=6e298e72d16587b721abb30bbf7c721a&limit=5")
    suspend fun getCityName(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?
    ): Response<List<MainModelGeo>>


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