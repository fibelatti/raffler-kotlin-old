package com.fibelatti.raffler.domain.preferences.mapper

import com.fibelatti.raffler.presentation.models.Preferences

object PreferencesMapper {
    fun toPresentationModel(rouletteMusicEnabled: Boolean,
             crashReportEnabled: Boolean,
             analyticsEnabled: Boolean,
             numberRangeEnabled: Boolean): Preferences {
        return Preferences(rouletteMusicEnabled, crashReportEnabled, analyticsEnabled, numberRangeEnabled)
    }
}
