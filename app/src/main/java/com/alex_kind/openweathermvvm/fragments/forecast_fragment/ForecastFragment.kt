package com.alex_kind.openweathermvvm.fragments.forecast_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alex_kind.openweathermvvm.R
import com.alex_kind.openweathermvvm.databinding.FragmentCurrentWeatherBinding
import com.alex_kind.openweathermvvm.databinding.FragmentForecastBinding
import com.alex_kind.openweathermvvm.fragments.FragmentsViewModel

class ForecastFragment : Fragment() {

    private lateinit var _bind: FragmentForecastBinding
    private val bind get() = _bind

    private val fragmentViewModel: FragmentsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentForecastBinding.inflate(inflater, container, false)
        return bind.root
    }

}