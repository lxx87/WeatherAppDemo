package com.example.weatherapp.request

data class ForecastResult(val city: City, val list: List<Forecast>)

data class City(
    val id: Long, val name: String, val coordinates: Coordinates,
    val country: String, val population: Int
)

data class Coordinates(val lon: String, val population: Int)

data class Temperature(
    val day: Float, val min: Float, val max: Float,
    val night: Float, val eve: Float, val morning: Float
)

data class Weather(val id: Long, val main: String, val description: String, val icon: String)

data class Forecast(
    val dt: Long, val temp: Temperature, val pressure: Float,
    val humidity: Int, val weather: List<Weather>, val speed: Float,
    val deg: Int, val clouds: Int, val rain: Float
)