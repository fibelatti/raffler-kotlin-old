package com.fibelatti.raffler.data.preferences

import com.fibelatti.raffler.core.UserPreferencesContract
import io.reactivex.Single
import javax.inject.Inject

class PreferencesRepository @Inject constructor(private val userPreferencesContract: UserPreferencesContract) : PreferencesRepositoryContract {
    override fun getRouletteMusicEnabled(): Single<Boolean> = Single.just(userPreferencesContract.getRouletteMusicEnabled())

    override fun setRouletteMusicEnabled(value: Boolean): Single<Boolean> {
        userPreferencesContract.setRouletteMusicEnabled(value)

        return Single.just(true)
    }

    override fun getCrashReportEnabled(): Single<Boolean> = Single.just(userPreferencesContract.getCrashReportEnabled())

    override fun setCrashReportEnabled(value: Boolean): Single<Boolean> {
        userPreferencesContract.setCrashReportEnabled(value)

        return Single.just(true)
    }

    override fun getAnalyticsEnabled(): Single<Boolean> = Single.just(userPreferencesContract.getAnalyticsEnabled())

    override fun setAnalyticsEnabled(value: Boolean): Single<Boolean> {
        userPreferencesContract.setAnalyticsEnabled(value)

        return Single.just(true)
    }

    override fun getNumberRangeEnabled(): Single<Boolean> = Single.just(userPreferencesContract.getNumberRangeEnabled())

    override fun setNumberRangeEnabled(value: Boolean): Single<Boolean> {
        userPreferencesContract.setNumberRangeEnabled(value)

        return Single.just(true)
    }
}
