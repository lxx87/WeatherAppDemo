package com.example.weatherapp.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import com.example.weatherapp.domain.Forecast
import com.example.weatherapp.domain.RequestDayForecastCommand
import com.example.weatherapp.extension.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class DetailActivity : AppCompatActivity() {
    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        title = intent.getStringExtra(CITY_NAME)
        async {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread {
                bindForecast(result)
            }
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(this@DetailActivity).load(iconUrl).into(icon)
        supportActionBar?.subtitle = date.toLong().toDateString()
        weatherDescription.text = description
        bindWeather(high to maxTemperature,low to minTemperature)
    }

    @SuppressLint("SetTextI18n")
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}°"
        it.second.setTextColor(
            ContextCompat.getColor(
                this,
                when (it.first) {
                    in -50..0 -> android.R.color.holo_red_dark
                    in 0..15 -> android.R.color.holo_orange_dark
                    else -> android.R.color.holo_green_dark
                }
            )
        )
    }
}
