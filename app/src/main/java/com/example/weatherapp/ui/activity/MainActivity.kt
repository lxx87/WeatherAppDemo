package com.example.weatherapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.ui.adapter.ForecastListAdapter
import com.example.weatherapp.R
import com.example.weatherapp.domain.RequestForecastCommand
import com.example.weatherapp.ui.toolbar.ToolbarManager
import org.jetbrains.anko.*
import    kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
        async {
            val result = RequestForecastCommand("94043").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result) {
                    //toast("click item ${it.date}")
                    startActivity<DetailActivity>(
                        DetailActivity.ID to it.id,
                        DetailActivity.CITY_NAME to result.city
                    )
                }
                toolbarTitle = "${result.city}(${result.country})"
            }
        }
    }
}
