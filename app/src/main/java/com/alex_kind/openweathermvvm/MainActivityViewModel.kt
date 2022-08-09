package com.alex_kind.openweathermvvm

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alex_kind.openweathermvvm.const.PERMISSION_REQUEST_ACCESS_LOCATION
import com.alex_kind.openweathermvvm.const.TAG
import com.alex_kind.openweathermvvm.models.geo.MainModelGeo
import com.alex_kind.openweathermvvm.retrofit.MainRepository
import com.google.android.gms.location.*
import kotlinx.coroutines.*

@SuppressLint("StaticFieldLeak")
class MainActivityViewModel(
    private val mainRepository: MainRepository,
    application: Application,      //TODO need remove application ->
    private val activity: Activity // and activity
) : AndroidViewModel(application) {

    private val context = application

    // START LOCATION'S VARIABLES
    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private lateinit var locationRequest: LocationRequest

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
        }
    }
    //END LOCATION'S VARIABLES

    //Create coroutines
    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    private val coroutineScope: CoroutineScope = CoroutineScope(coroutineContext)


    //START OTHER VARIABLES
    private val _lat = MutableLiveData<String>()
    val lat: LiveData<String> get() = _lat

    private val _lon = MutableLiveData<String>()
    val lon: LiveData<String> get() = _lon

    private val _cityName = MutableLiveData<List<MainModelGeo>>()
    val cityName: LiveData<List<MainModelGeo>> get() = _cityName


    val loading = MutableLiveData<Boolean>()
    //END OTHER VARIABLES


    init {
        getCurrentLocation()
    }


    private fun getCityName() {
        coroutineScope.launch {
            loading.postValue(true)
            val response = mainRepository.getCityName(_lat.value, _lon.value)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _cityName.postValue(response.body())
                    loading.value = false
                } else {
                    loading.value = false
                }

            }
        }
    }


    fun getLocationUpdates() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest = LocationRequest.create()
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.priority = Priority.PRIORITY_HIGH_ACCURACY


        Looper.myLooper()?.let {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, it
            )
        }
        getCurrentLocation()
    }


    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }

                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    val location: Location? = it.result

                    if (location == null) {
                        Toast.makeText(context, "Null received", Toast.LENGTH_SHORT).show()
                        getLocationUpdates()
                    } else {
                        Toast.makeText(context, "get success", Toast.LENGTH_SHORT).show()

                        _lat.value = location.latitude.toString()
                        _lon.value = location.longitude.toString()
                        getCityName()
                        Log.d(TAG, "VIEW MODEL RESTARTED")
                    }
                }
            } else {
                Toast.makeText(context, "Turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(context, intent, null)
            }
        } else {

            requestPermission()
        }

    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )


    }


    //PERMISSIONS
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }


    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }


}