package com.fibelatti.raffler.domain.preferences

import com.fibelatti.raffler.data.preferences.PreferencesRepositoryContract
import com.fibelatti.raffler.presentation.models.Preferences
import io.reactivex.Single
import io.reactivex.functions.Function4
import javax.inject.Inject

class GetPreferencesUseCase @Inject constructor(private val preferencesRepository: PreferencesRepositoryContract) {
    fun getPreferences(): Single<Preferences> =
            Single.zip(
                    preferencesRepository.getRouletteMusicEnabled(),
                    preferencesRepository.getCrashReportEnabled(),
                    preferencesRepository.getAnalyticsEnabled(),
                    preferencesRepository.getNumberRangeEnabled(),
                    Function4 { rouletteMusicEnabled, crashReportEnabled, analyticsEnabled, numberRangeEnabled ->
                        PreferencesMapper.toPresentationModel(rouletteMusicEnabled, crashReportEnabled, analyticsEnabled, numberRangeEnabled)
                    })
}
