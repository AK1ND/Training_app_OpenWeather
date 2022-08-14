package com.alex_kind.openweathermvvm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alex_kind.openweathermvvm.fragments.current_weather_fragment.CurrentWeatherFragment
import com.alex_kind.openweathermvvm.fragments.forecast_fragment.ForecastFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CurrentWeatherFragment()
            1 -> ForecastFragment()
            else -> return CurrentWeatherFragment()
        }
    }


}