package com.example.weatherapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.ui.adapter.ForecastListAdapter
import com.example.weatherapp.R
import com.example.weatherapp.domain.RequestForecastCommand
import com.example.weatherapp.domain.Forecast
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private val items = listOf(
        "Mon	6/23	-	Sunny	-	31/17",
        "Tue	6/24	-	Foggy	-	21/8",
        "Wed	6/25	-	Cloudy	-	22/17",
        "Thurs	6/26	-	Rainy	-	18/11",
        "Fri	6/27	-	Foggy	-	21/10",
        "Sat	6/28	-	TRAPPED	IN	WEATHERSTATION	-	23/18",
        "Sun	6/29	-	Sunny	-	20/7"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastList = find<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)
        async{
            val result= RequestForecastCommand("94043").execute()
            uiThread {
                forecastList.adapter= ForecastListAdapter(result,
                    object : ForecastListAdapter.OnItemClickListener {
                        override fun invoke(forecast: Forecast) {
                            toast("click item ${forecast.date}")
                        }
                    })
            }
        }
    }
}
