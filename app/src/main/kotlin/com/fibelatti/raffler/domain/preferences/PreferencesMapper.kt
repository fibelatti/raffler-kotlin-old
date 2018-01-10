package com.fibelatti.raffler.domain.preferences

import com.fibelatti.raffler.presentation.models.Preferences

object PreferencesMapper {
    fun toPresentationModel(rouletteMusicEnabled: Boolean,
                            crashReportEnabled: Boolean,
                            analyticsEnabled: Boolean,
                            numberRangeEnabled: Boolean) =
        Preferences(rouletteMusicEnabled, crashReportEnabled, analyticsEnabled, numberRangeEnabled)
}
