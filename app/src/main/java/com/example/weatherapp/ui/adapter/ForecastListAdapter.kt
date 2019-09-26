package com.example.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.Forecast
import com.example.weatherapp.domain.ForecastList
import com.example.weatherapp.extension.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(private val forecastList: ForecastList, private val itemClick: (Forecast)->Unit) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(
            R.layout.item_forecast,parent,false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int = forecastList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(forecastList[position])
    }

    class ViewHolder(private val view: View, val itemClick: (Forecast)->Unit) : RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.with(view.context).load(iconUrl).into(view.icon)
                view.date.text=date.toLong().toDateString()
                view.description.text=description
                view.maxTemperature.text="$high°"
                view.minTemperature.text="$low°"
                view.setOnClickListener {itemClick(forecast)}
            }
        }

    }

}

