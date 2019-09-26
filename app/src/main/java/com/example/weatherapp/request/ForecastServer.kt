package com.example.weatherapp.request

import com.example.weatherapp.db.DataMapper
import com.example.weatherapp.db.ForecastDb
import com.example.weatherapp.domain.ForecastList
import com.example.weatherapp.domain.datasource.ForecastDataSource

class ForecastServer(
    private val dataMapper: ForecastDataMapper = ForecastDataMapper(),
    private val forecastDb: ForecastDb = ForecastDb()
) :
    ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode.toString()).execute()
        val converted = dataMapper.convertFromDataModel(zipCode,result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }
}