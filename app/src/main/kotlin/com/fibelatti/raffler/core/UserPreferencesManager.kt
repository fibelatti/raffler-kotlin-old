package com.fibelatti.raffler.core

import android.content.SharedPreferences

class UserPreferencesManager(private val sharedPreferences: SharedPreferences) : UserPreferencesContract {
    companion object {
        private const val USER_PREFERENCES_ROULETTE_MUSIC_ENABLED = "user_preferences_roulette_music_enabled"
        private const val USER_PREFERENCES_CRASH_REPORT_ENABLED = "user_preferences_crash_report_enabled"
        private const val USER_PREFERENCES_ANALYTICS_ENABLED = "user_preferences_analytics_enabled"
        private const val USER_PREFERENCES_NUMBER_RANGE_ENABLED = "user_preferences_number_range_enabled"
    }

    override fun getRouletteMusicEnabled(): Boolean
            = sharedPreferences.getBoolean(USER_PREFERENCES_ROULETTE_MUSIC_ENABLED, true)

    override fun setRouletteMusicEnabled(value: Boolean): UserPreferencesContract {
        sharedPreferences.applyWithEditor {
            putBoolean(USER_PREFERENCES_ROULETTE_MUSIC_ENABLED, value)
        }

        return this
    }

    override fun getCrashReportEnabled(): Boolean
            = sharedPreferences.getBoolean(USER_PREFERENCES_CRASH_REPORT_ENABLED, true)

    override fun setCrashReportEnabled(value: Boolean): UserPreferencesContract {
        sharedPreferences.applyWithEditor {
            putBoolean(USER_PREFERENCES_CRASH_REPORT_ENABLED, value)
        }

        return this
    }

    override fun getAnalyticsEnabled(): Boolean
            = sharedPreferences.getBoolean(USER_PREFERENCES_ANALYTICS_ENABLED, true)

    override fun setAnalyticsEnabled(value: Boolean): UserPreferencesContract {
        sharedPreferences.applyWithEditor {
            putBoolean(USER_PREFERENCES_ANALYTICS_ENABLED, value)
        }

        return this
    }

    override fun getNumberRangeEnabled(): Boolean
            = sharedPreferences.getBoolean(USER_PREFERENCES_NUMBER_RANGE_ENABLED, true)

    override fun setNumberRangeEnabled(value: Boolean): UserPreferencesContract {
        sharedPreferences.applyWithEditor {
            putBoolean(USER_PREFERENCES_NUMBER_RANGE_ENABLED, value)
        }

        return this
    }
}
