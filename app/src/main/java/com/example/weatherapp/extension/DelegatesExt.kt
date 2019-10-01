package com.example.weatherapp.extension

import android.content.Context
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


object DelegatesExt {
    fun <T> notNullSingleValue(): ReadWriteProperty<Any?, T> = NotNullSingleValueVar()

    fun <T : Any> preference(context: Context, name: String, default: T)
            = Preference(context, name, default)
}

/**
 * 只允许初始化一次的变量
 */
private class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("${property.name} not initialized ")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("${property.name} already initialized")
    }

}


class Preference<T>(private val context: Context, private val name: String, private val default: T) :
    ReadWriteProperty<Any?, T> {

    val prefs by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    @Suppress("UNCHECKED_CAST")
    private fun findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type cannot be saved into Preference")
        }
        res as T
    }

    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type cannot be saved into Preference")
        }.apply()
    }
}