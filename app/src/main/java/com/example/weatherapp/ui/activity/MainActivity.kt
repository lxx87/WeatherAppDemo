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
import com.example.weatherapp.extension.DelegatesExt

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    private val zipCode: Long by DelegatesExt.preference(
        this, SettingsActivity.ZIP_CODE,
        SettingsActivity.DEFAULT_ZIP
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = async {
        val result = RequestForecastCommand(zipCode.toString()).execute()
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
