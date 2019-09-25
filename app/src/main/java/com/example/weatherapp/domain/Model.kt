package com.example.weatherapp.domain

data class ForecastList(val city: String, val country: String, val dailyForecast: List<Forecast>) {

    /**
     * 重载[]操作符
     */
    operator fun get(position: Int): Forecast = dailyForecast[position]

    val size: Int
        get() = dailyForecast.size
}

data class Forecast(val date: String, val description: String, val high: Int,
                    val low: Int, val iconUrl: String)