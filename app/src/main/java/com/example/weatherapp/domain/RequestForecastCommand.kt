package com.example.weatherapp.domain

import android.util.Log
import com.example.weatherapp.domain.datasource.ForecastProvider

class RequestForecastCommand(
    private val zipCode: String,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) : Command<ForecastList> {
    override fun execute(): ForecastList {
        Log.d("RequestForecastCommand",zipCode)
        return forecastProvider.requestByZipCode(zipCode.toLong(), 7)
    }
}