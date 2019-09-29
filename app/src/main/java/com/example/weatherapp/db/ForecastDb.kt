package com.example.weatherapp.db

import android.util.Log
import com.example.weatherapp.domain.Forecast
import com.example.weatherapp.domain.ForecastList
import com.example.weatherapp.domain.datasource.ForecastDataSource
import com.example.weatherapp.extension.clear
import com.example.weatherapp.extension.parseList
import com.example.weatherapp.extension.parseOpt
import com.example.weatherapp.extension.toVarargArray
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(
    private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
    private val dataMapper: DataMapper = DataMapper()
):ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        Log.d("requestDate",date.toString())
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? " + "AND ${DayForecastTable.DATE}>= ?"
        val dailyForecast = select(DayForecastTable.NAME)
            .whereSimple(dailyRequest, zipCode.toString(), date.toString()).parseList { DayForecast(HashMap(it)) }
        val city = select(CityForecastTable.NAME)
            .whereSimple("${CityForecastTable.ID}=?", zipCode.toString())
            .parseOpt { CityForecast(HashMap(it), dailyForecast) }
        if (city != null) dataMapper.convertToDomain(city) else null
    }

    /**
     * 将domain层的数据存回数据库
     */
    fun saveForecast(forecast:ForecastList)=forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)
        with(dataMapper.convertFromDomain(forecast)){
            insert(CityForecastTable.NAME,*map.toVarargArray())
            dailyForecast.forEach{insert(DayForecastTable.NAME,*it.map.toVarargArray())}
        }
    }

    override fun requestDayForecast(id: Long): Forecast? =forecastDbHelper.use {
        val forecast=select(DayForecastTable.NAME).
            whereSimple("_id = ?",id.toString()).parseOpt{DayForecast(HashMap(it))}
        forecast?.let { dataMapper.convertDayToDomain(it) }
    }
}