package com.example.weatherapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.weatherapp.App
import org.jetbrains.anko.db.*

class ForecastDbHelper(context: Context = App.instance) : ManagedSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        with(CityForecastTable) {
            db.createTable(
                NAME, true,
                ID to INTEGER + PRIMARY_KEY,
                CITY to TEXT,
                COUNTRY to TEXT
            )
        }

        with(DayForecastTable) {
            db.createTable(
                NAME, true,
                ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DATE to INTEGER,
                DESCRIPTION to TEXT,
                HIGH to INTEGER,
                LOW to INTEGER,
                ICON_URL to TEXT,
                CITY_ID to INTEGER
            )
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(CityForecastTable.NAME, true)
        db.dropTable(DayForecastTable.NAME, true)
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "forecast.db"
        const val DB_VERSION = 1
        val instance: ForecastDbHelper by lazy { ForecastDbHelper() }
    }


}

