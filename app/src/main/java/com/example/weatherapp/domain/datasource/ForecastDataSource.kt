package com.example.weatherapp.domain.datasource

import com.example.weatherapp.domain.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode:Long,date:Long): ForecastList?
}