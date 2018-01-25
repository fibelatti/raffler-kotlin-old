package com.fibelatti.raffler.presentation.home

import com.fibelatti.raffler.presentation.base.BaseContract
import com.fibelatti.raffler.presentation.common.ObservableView

interface NavigationContract {
    interface Presenter : BaseContract.Presenter<View>

    interface View : BaseContract.View {
        // Produces
        fun quickDecisionsClicked(): ObservableView<Unit>

        fun groupsClicked(): ObservableView<Unit>

        fun preferencesClicked(): ObservableView<Unit>

        // Consumes
        fun goToQuickDecisions()

        fun goToGroups()

        fun goToPreferences()
    }
}
