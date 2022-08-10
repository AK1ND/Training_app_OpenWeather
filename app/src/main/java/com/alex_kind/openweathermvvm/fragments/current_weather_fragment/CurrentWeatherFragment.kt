package com.alex_kind.openweathermvvm.fragments.current_weather_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alex_kind.openweathermvvm.databinding.FragmentCurrentWeatherBinding


class CurrentWeatherFragment : Fragment() {

    private lateinit var _bind: FragmentCurrentWeatherBinding
    private val bind get() = _bind

    private val fragmentViewModel: CurrentWeatherFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setParams()
    }

    private fun setParams() {
        fragmentViewModel.cityData.observe(viewLifecycleOwner, {
            bind.testTxt.text = it.name
        })
    }
}