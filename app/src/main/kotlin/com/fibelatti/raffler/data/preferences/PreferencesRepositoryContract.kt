package com.fibelatti.raffler.data.preferences

import io.reactivex.Single

interface PreferencesRepositoryContract {
    fun getRouletteMusicEnabled(): Single<Boolean>

    fun setRouletteMusicEnabled(value: Boolean): Single<Boolean>

    fun getCrashReportEnabled(): Single<Boolean>

    fun setCrashReportEnabled(value: Boolean): Single<Boolean>

    fun getAnalyticsEnabled(): Single<Boolean>

    fun setAnalyticsEnabled(value: Boolean): Single<Boolean>

    fun getNumberRangeEnabled(): Single<Boolean>

    fun setNumberRangeEnabled(value: Boolean): Single<Boolean>
}
