package com.alex_kind.openweathermvvm

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alex_kind.openweathermvvm.retrofit.MainRepository
import com.alex_kind.openweathermvvm.view_models.MainActivityViewModel

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(
    private val repository: MainRepository,
    private val application: Application,
    private val activity: Activity
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            MainActivityViewModel(repository, application, activity) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }

}