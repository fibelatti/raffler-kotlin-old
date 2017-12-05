package com.fibelatti.raffler.presentation.preferences

import com.fibelatti.raffler.presentation.base.BaseContract
import com.fibelatti.raffler.presentation.models.Preferences

interface PreferencesContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getPreferences()

        fun updatePreferences(preferences: Preferences)
    }

    interface View : BaseContract.View {
        fun onPreferencesFetched(preferences: Preferences)

        fun onPreferencesUpdated()
    }
}
