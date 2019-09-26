package com.example.weatherapp.request

import com.example.weatherapp.domain.ForecastList
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.example.weatherapp.domain.Forecast as ModelForecast

/**
 * 处理请求回来的数据与domain层对象的转换
 */
class ForecastDataMapper {
    fun convertFromDataModel(zipCode:Long,forecastResult: ForecastResult): ForecastList {
        return ForecastList(zipCode,
            forecastResult.city.name, forecastResult.city.country,
            convertForecastListToDomain(forecastResult.list)
        )
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(
            -1,forecast.dt.toString(), forecast.weather[0].description,
            forecast.temp.max.toInt(), forecast.temp.min.toInt(),
            generateIconUrl(forecast.weather[0].icon)
        )
    }

    private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"
}