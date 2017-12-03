package com.fibelatti.raffler.presentation.home

import com.fibelatti.raffler.presentation.base.BaseContract

interface NavigationContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun onQuickDecisionsClicked()

        fun onGroupsClicked()

        fun onPreferencesClicked()
    }

    interface View : BaseContract.View {
        fun goToQuickDecisions()

        fun goToGroups()

        fun goToPreferences()
    }
}
