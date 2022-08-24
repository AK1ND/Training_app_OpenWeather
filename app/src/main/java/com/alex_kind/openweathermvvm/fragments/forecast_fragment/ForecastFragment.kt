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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ForecastFragment : Fragment() {

    private var _bind: FragmentForecastBinding? = null
    private val bind get() = _bind!!

    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    private val coroutineScope: CoroutineScope = CoroutineScope(coroutineContext)

    private val fragmentViewModel: FragmentsViewModel by activityViewModels()

    private lateinit var dbViewModel: DatabaseViewModel

    private lateinit var adapter: ForecastAdapter
    private lateinit var dbAdapter: ForecastAdapterDatabase


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
        adapter = ForecastAdapter(dbViewModel, requireContext())
        dbAdapter = ForecastAdapterDatabase()

        fragmentViewModel.errorLoading.observe(viewLifecycleOwner){
            if (it){
                bind.recyclerView.adapter = dbAdapter
            } else {
                bind.recyclerView.adapter = adapter
            }
        }

        fragmentViewModel.forecastWeatherData.observe(viewLifecycleOwner) {
            adapter.setForecast(it.list)
        }
        dbViewModel.readAllWeatherData.observe(viewLifecycleOwner) {
            adapter.setDatabaseForInput(it)
        }

        dbViewModel.readAllWeatherData.observe(viewLifecycleOwner){
            dbAdapter.setDatabaseForOutput(it)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

}