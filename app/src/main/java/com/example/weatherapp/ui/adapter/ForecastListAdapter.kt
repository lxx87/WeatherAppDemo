package com.example.weatherapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.Forecast
import com.example.weatherapp.domain.ForecastList
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class ForecastListAdapter(private val forecastList: ForecastList, private val itemClick: OnItemClickListener) :
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

    class ViewHolder(private val view: View, val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        private val iconView: ImageView = view.find(R.id.icon)
        private val dateView: TextView = view.find(R.id.date)
        private val descriptionView: TextView = view.find(R.id.description)
        private val maxTemperatureView: TextView = view.find(R.id.maxTemperature)
        private val minTemperatureView: TextView = view.find(R.id.minTemperature)

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Log.d("ForecastAdapter","bind ${forecast.date}")
                Picasso.with(view.context).load(iconUrl).into(iconView)
                dateView.text = date
                descriptionView.text = description
                maxTemperatureView.text = high.toString()
                minTemperatureView.text = low.toString()
                view.setOnClickListener { itemClick(forecast) }
            }
        }

    }

    public interface OnItemClickListener {
        operator fun invoke(forecast: Forecast)
    }
}

