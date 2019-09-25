package com.example.weatherapp.domain

import com.example.weatherapp.request.ForecastDataMapper
import com.example.weatherapp.request.ForecastRequest

class RequestForecastCommand(val zipCode:String):Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest= ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}