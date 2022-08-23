package com.alex_kind.openweathermvvm.fragments.forecast_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.alex_kind.openweathermvvm.databinding.FragmentForecastBinding
import com.alex_kind.openweathermvvm.view_models.DatabaseViewModel
import com.alex_kind.openweathermvvm.view_models.FragmentsViewModel

class ForecastFragment : Fragment() {

    private var _bind: FragmentForecastBinding? = null
    private val bind get() = _bind!!


    private val fragmentViewModel: FragmentsViewModel by activityViewModels()

    private lateinit var dbViewModel: DatabaseViewModel

    private lateinit var adapter: ForecastAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentForecastBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbViewModel = ViewModelProvider(this)[DatabaseViewModel::class.java]
        adapter = ForecastAdapter(dbViewModel)

        bind.recyclerView.adapter = adapter

        fragmentViewModel.forecastWeatherData.observe(viewLifecycleOwner) {
            adapter.setForecast(it.list)
        }
        dbViewModel.readAllWeatherData.observe(viewLifecycleOwner) {
            adapter.setDatabase(it)
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

}