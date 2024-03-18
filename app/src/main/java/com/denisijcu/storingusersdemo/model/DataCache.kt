package com.denisijcu.storingusersdemo.model


// DataCache.kt
class DataCache<T> {
    private var cachedData: T? = null

    fun getData(): T? {
        return cachedData
    }

    fun setData(data: T) {
        cachedData = data
    }
}
