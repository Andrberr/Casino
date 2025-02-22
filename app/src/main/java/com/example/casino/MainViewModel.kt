package com.example.casino

import android.content.Context
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    fun saveMode(context: Context, value: String) {
        getPrefs(context).edit().putString(MODE_KEY, value).apply()
    }

    fun getMode(context: Context): String {
        return getPrefs(context).getString(MODE_KEY, null).orEmpty()
    }

    fun saveContentUrl(context: Context, value: String) {
        getPrefs(context).edit().putString(CONTENT_URL, value).apply()
    }

    fun getContentUrl(context: Context): String {
        return getPrefs(context).getString(CONTENT_URL, null).orEmpty()
    }

    private fun getPrefs(context: Context) =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    private companion object {
        const val MODE_KEY = "mode"
        const val CONTENT_URL = "content_url"
    }
}