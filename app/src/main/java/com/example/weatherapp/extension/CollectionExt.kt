package com.example.weatherapp.extension

fun <K, V : Any> MutableMap<K, V?>.toVarargArray(): Array<Pair<K, V>> =
    map { Pair(it.key, it.value!!) }.toTypedArray()

inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?): R {
    for (element in this) {
        val result = predicate(element)
        if (result != null) return result
    }
    throw NoSuchElementException("No element matching predicate found.")
}