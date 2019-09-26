package com.example.weatherapp.db

import com.example.weatherapp.domain.Forecast
import com.example.weatherapp.domain.ForecastList

class DataMapper {
    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        ForecastList(_id, city, country, dailyForecast.map { convertDayToDomain(it) })
    }

    private fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(_id,date.toString(), description, high, low, iconUrl)
    }

    fun convertFromDomain(forecast:ForecastList)= with(forecast){
        val daily=dailyForecast.map { convertDayFromDomain(id,it) }
        CityForecast(id,city,country,daily)
    }

    private fun convertDayFromDomain(cityId:Long,forecast:Forecast)= with(forecast){
        DayForecast(date.toLong(),description,high,low,iconUrl,cityId)
    }
}