package com.fibelatti.raffler.data.preferences

import com.fibelatti.raffler.data.localdatastorage.UserPreferencesContract
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PreferencesRepository @Inject constructor(private val userPreferencesContract: UserPreferencesContract) : PreferencesRepositoryContract {
    override fun getRouletteMusicEnabled(): Single<Boolean> = Single.just(userPreferencesContract.getRouletteMusicEnabled())

    override fun setRouletteMusicEnabled(value: Boolean): Completable {
        userPreferencesContract.setRouletteMusicEnabled(value)

        return Completable.complete()
    }

    override fun getCrashReportEnabled(): Single<Boolean> = Single.just(userPreferencesContract.getCrashReportEnabled())

    override fun setCrashReportEnabled(value: Boolean): Completable {
        userPreferencesContract.setCrashReportEnabled(value)

        return Completable.complete()
    }

    override fun getAnalyticsEnabled(): Single<Boolean> = Single.just(userPreferencesContract.getAnalyticsEnabled())

    override fun setAnalyticsEnabled(value: Boolean): Completable {
        userPreferencesContract.setAnalyticsEnabled(value)

        return Completable.complete()
    }

    override fun getNumberRangeEnabled(): Single<Boolean> = Single.just(userPreferencesContract.getNumberRangeEnabled())

    override fun setNumberRangeEnabled(value: Boolean): Completable {
        userPreferencesContract.setNumberRangeEnabled(value)

        return Completable.complete()
    }
}
