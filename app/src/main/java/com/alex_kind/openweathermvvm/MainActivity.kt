package com.alex_kind.openweathermvvm

import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alex_kind.openweathermvvm.const.PERMISSION_REQUEST_ACCESS_LOCATION
import com.alex_kind.openweathermvvm.databinding.ActivityMainBinding
import com.alex_kind.openweathermvvm.fragments.current_weather_fragment.CurrentWeatherFragment
import com.alex_kind.openweathermvvm.fragments.FragmentsViewModel
import com.alex_kind.openweathermvvm.retrofit.MainRepository
import com.alex_kind.openweathermvvm.retrofit.RetrofitService

open class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel
    private val fragmentsViewModel: FragmentsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val retrofitService = RetrofitService.getRetrofit()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(
            this,
            MainActivityViewModelFactory(mainRepository, applicationContext as Application, this)
        )[MainActivityViewModel::class.java]


        setParams()



//        bind.buttonCheck.setOnClickListener {
//            viewModel.getLocationUpdates()
//            setParams()
//            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CurrentWeatherFragment()).commit()
//
//        }
    }


    private fun setParams() {
        viewModel.currentWeatherData.observe(this) {
            fragmentsViewModel.setDataCurrentWeather(it)
        }

        viewModel.forecastData.observe(this) {
            fragmentsViewModel.setDataForecast(it)
            loading()
        }
    }


    private fun loading() {
        viewModel.loading.observe(this) {
            if (it) {
                bind.progressBar.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CurrentWeatherFragment()).commit()
            }
        }

    }


    override fun onResume() {
        super.onResume()
        if (bind.progressBar.visibility == View.VISIBLE) {
            viewModel.getLocationUpdates()
            setParams()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
        }
    }
}