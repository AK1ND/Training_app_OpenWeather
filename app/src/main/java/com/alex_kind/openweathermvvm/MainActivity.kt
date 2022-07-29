package com.alex_kind.openweathermvvm

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alex_kind.openweathermvvm.Retrofit.MainRepository
import com.alex_kind.openweathermvvm.Retrofit.RetrofitService
import com.alex_kind.openweathermvvm.const.PERMISSION_REQUEST_ACCESS_LOCATION
import com.alex_kind.openweathermvvm.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class MainActivity : AppCompatActivity() {

    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    private val coroutineScope: CoroutineScope = CoroutineScope(coroutineContext)

    lateinit var bind: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    var lat = ""
    var lon = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val retrofitService = RetrofitService.getRetrofit()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(mainRepository, applicationContext as Application, this))
            .get(MainActivityViewModel::class.java)



        getLocation()


        bind.buttonCheck.setOnClickListener {
            viewModel.getLocationUpdates()
        }
    }





    private fun setParams() {
            bind.locationTxt.text = "lat: $lat\nlon: $lon"
        viewModel.cityName.observe(this){
            bind.test.text = it[0].name
        }
    }


    private fun getLocation() {
        if (isLocationEnabled()) {
            viewModel.getCurrentLocation()

            viewModel.lat.observe(this, {
                lat = it
            })

            viewModel.lon.observe(this, {
                lon = it
                setParams()
            })

        } else {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }







    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )


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
                getLocation()
            }
        } else {
            Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
        }
    }
}