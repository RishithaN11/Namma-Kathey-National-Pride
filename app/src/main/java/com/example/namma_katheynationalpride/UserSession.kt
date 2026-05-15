package com.example.namma_katheynationalpride

import android.content.Context

class UserSession(context: Context) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(name: String) {
        prefs.edit().putString("username", name).apply()
    }

    fun getUser(): String? {
        return prefs.getString("username", null)
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }
}