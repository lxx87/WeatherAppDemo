package com.example.weatherapp.domain

import com.example.weatherapp.domain.datasource.ForecastProvider

class RequestForecastCommand(
    private val zipCode: String,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) : Command<ForecastList> {
    override fun execute(): ForecastList {

        return forecastProvider.requestByZipCode(zipCode.toLong(), 7)
    }
}