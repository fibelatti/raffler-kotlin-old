package com.fibelatti.raffler.domain.preferences

import com.fibelatti.raffler.data.preferences.PreferencesRepositoryContract
import com.fibelatti.raffler.presentation.models.Preferences
import io.reactivex.Completable

class UpdatePreferencesUseCase(private val preferencesRepository: PreferencesRepositoryContract) {
    fun updatePreferences(preferences: Preferences): Completable =
        Completable.merge(listOf(
            preferencesRepository.setRouletteMusicEnabled(preferences.rouletteMusicEnabled),
            preferencesRepository.setCrashReportEnabled(preferences.crashReportEnabled),
            preferencesRepository.setAnalyticsEnabled(preferences.analyticsEnabled),
            preferencesRepository.setNumberRangeEnabled(preferences.numberRangeEnabled)))
}
