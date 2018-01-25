package com.fibelatti.raffler.presentation.quickdecisions

import com.fibelatti.raffler.presentation.base.BaseContract
import com.fibelatti.raffler.presentation.common.ObservableView
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision

interface QuickDecisionsContract {
    interface Presenter : BaseContract.Presenter<View>

    interface View : BaseContract.View {
        // Produces
        fun quickDecisionClicked(): ObservableView<QuickDecision>

        fun addNewClicked(): ObservableView<Unit>

        fun createGroup(): ObservableView<Group>

        // Consumes
        fun onDataLoaded(quickDecisions: List<QuickDecision>)

        fun onQuickDecisionResult(result: String, isOdd: Boolean)

        fun onDisplayUserGroups(groups: List<Group>)

        fun onGroupCreationRequired()

        fun onQuickDecisionsUpdated(quickDecisions: List<QuickDecision>)
    }
}
