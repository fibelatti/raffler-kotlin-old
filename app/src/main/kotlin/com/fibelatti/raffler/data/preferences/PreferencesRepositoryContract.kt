package com.fibelatti.raffler.data.preferences

import io.reactivex.Completable
import io.reactivex.Single

interface PreferencesRepositoryContract {
    fun getRouletteMusicEnabled(): Single<Boolean>

    fun setRouletteMusicEnabled(value: Boolean): Completable

    fun getCrashReportEnabled(): Single<Boolean>

    fun setCrashReportEnabled(value: Boolean): Completable

    fun getAnalyticsEnabled(): Single<Boolean>

    fun setAnalyticsEnabled(value: Boolean): Completable

    fun getNumberRangeEnabled(): Single<Boolean>

    fun setNumberRangeEnabled(value: Boolean): Completable
}
