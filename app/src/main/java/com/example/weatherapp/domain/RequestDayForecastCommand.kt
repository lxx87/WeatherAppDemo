package com.example.weatherapp.domain

import com.example.weatherapp.domain.datasource.ForecastProvider

class RequestDayForecastCommand(
    private val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) :
    Command<Forecast> {
    override fun execute(): Forecast = forecastProvider.requestForecast(id)
}