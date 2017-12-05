package com.fibelatti.raffler.domain.preferences

import com.fibelatti.raffler.data.preferences.PreferencesRepositoryContract
import com.fibelatti.raffler.presentation.models.Preferences
import io.reactivex.Single
import io.reactivex.functions.Function4
import javax.inject.Inject

class UpdatePreferencesUseCase @Inject constructor(private val preferencesRepository: PreferencesRepositoryContract) {
    fun updatePreferences(preferences: Preferences): Single<Boolean> {
        return Single.zip(
                preferencesRepository.setRouletteMusicEnabled(preferences.rouletteMusicEnabled),
                preferencesRepository.setCrashReportEnabled(preferences.crashReportEnabled),
                preferencesRepository.setAnalyticsEnabled(preferences.analyticsEnabled),
                preferencesRepository.setNumberRangeEnabled(preferences.numberRangeEnabled),
                Function4 { _, _, _, _ ->
                    true
                })
    }
}
