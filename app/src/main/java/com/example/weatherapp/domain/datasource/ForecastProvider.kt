package com.example.weatherapp.domain.datasource

import com.example.weatherapp.db.ForecastDb
import com.example.weatherapp.domain.Forecast
import com.example.weatherapp.domain.ForecastList
import com.example.weatherapp.extension.firstResult
import com.example.weatherapp.request.ForecastServer

class ForecastProvider(private val sources: List<ForecastDataSource> = SOURCES) {
    companion object {
        const val DAY_IN_MILLS = 24 * 60 * 60 * 1000L
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList =
        sources.firstResult { requestSource(it, days, zipCode) }

    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if (res != null && res.size >= days) res else null
    }

    private fun todayTimeSpan() = (System.currentTimeMillis() / DAY_IN_MILLS)* DAY_IN_MILLS

    fun requestForecast(id:Long):Forecast=sources.firstResult { it.requestDayForecast(id) }
}