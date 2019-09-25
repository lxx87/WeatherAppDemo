package com.example.weatherapp.domain

interface Command<T> {
    fun execute():T
}