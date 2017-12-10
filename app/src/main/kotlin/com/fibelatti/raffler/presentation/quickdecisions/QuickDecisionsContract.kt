package com.fibelatti.raffler.presentation.quickdecisions

import com.fibelatti.raffler.presentation.base.BaseContract
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision

interface QuickDecisionsContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun bootstrap()

        fun getQuickDecisionResult(quickDecision: QuickDecision)

        fun addGroupToQuickDecisions(group: Group)
    }

    interface View : BaseContract.View {
        fun onDataLoaded(quickDecisions: List<QuickDecision>, groups: List<Group>)

        fun onQuickDecisionResult(result: String, isOdd: Boolean)

        fun onQuickDecisionsUpdated(quickDecisions: List<QuickDecision>)
    }
}
