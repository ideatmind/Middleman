package com.middleman.contracts.utils

import android.content.Context

object NotificationSharedPref {
    private const val LAST_SEEN_TIMESTAMP_KEY = "last_seen_timestamp"

    fun getLastSeenTimestamp(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
        return sharedPreferences.getLong(LAST_SEEN_TIMESTAMP_KEY, 0L) // Default to 0 if not set
    }

    fun setLastSeenTimestamp(context: Context, timestamp: Long) {
        val sharedPreferences = context.getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putLong(LAST_SEEN_TIMESTAMP_KEY, timestamp)
            apply()
        }
    }
}
