package com.denisijcu.storingusersdemo.model

import android.content.Context

// UserPreferences.kt
class UserPreferences(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

    fun saveUsername(username: String) {
        sharedPreferences.edit().putString("username", username).apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString("username", "") ?: ""
    }
}

