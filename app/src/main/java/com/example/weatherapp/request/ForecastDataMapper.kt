package com.example.weatherapp.request

import com.example.weatherapp.domain.ForecastList
import java.text.DateFormat
import java.util.*
import com.example.weatherapp.domain.Forecast as ModelForecast

/**
 * 处理请求回来的数据与domain层对象的转换
 */
class ForecastDataMapper {
    fun convertFromDataModel(forecastResult: ForecastResult): ForecastList {
        return ForecastList(
            forecastResult.city.name, forecastResult.city.country,
            convertForecastListToDomain(forecastResult.list)
        )
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) } +
                list.map { convertForecastItemToDomain(it) } +
                list.map { convertForecastItemToDomain(it) }//仅仅是让列表长一些
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(
            convertDate(forecast.dt), forecast.weather[0].description,
            forecast.temp.max.toInt(), forecast.temp.min.toInt(),
            generateIconUrl(forecast.weather[0].icon)
        )
    }

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }

    private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"
}