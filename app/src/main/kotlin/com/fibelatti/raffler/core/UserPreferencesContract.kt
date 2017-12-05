package com.fibelatti.raffler.core

interface UserPreferencesContract {
    fun getRouletteMusicEnabled(): Boolean

    fun setRouletteMusicEnabled(value: Boolean): UserPreferencesContract

    fun getCrashReportEnabled(): Boolean

    fun setCrashReportEnabled(value: Boolean): UserPreferencesContract

    fun getAnalyticsEnabled(): Boolean

    fun setAnalyticsEnabled(value: Boolean): UserPreferencesContract

    fun getNumberRangeEnabled(): Boolean

    fun setNumberRangeEnabled(value: Boolean): UserPreferencesContract
}
