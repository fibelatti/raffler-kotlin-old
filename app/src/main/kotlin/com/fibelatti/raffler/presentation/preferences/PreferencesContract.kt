package com.fibelatti.raffler.presentation.preferences

import com.fibelatti.raffler.presentation.base.BaseContract
import com.fibelatti.raffler.presentation.common.ObservableView
import com.fibelatti.raffler.presentation.models.Preferences

interface PreferencesContract {
    interface Presenter : BaseContract.Presenter<View>

    interface View : BaseContract.View {
        // Produces
        fun updatePreferences(): ObservableView<Preferences>

        // Consumes
        fun onPreferencesFetched(preferences: Preferences)

        fun onPreferencesUpdated()
    }
}
