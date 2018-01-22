package com.fibelatti.raffler.presentation.quickdecisions

import com.fibelatti.raffler.presentation.base.BaseContract
import com.fibelatti.raffler.presentation.models.AddNewPlaceholder
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision
import io.reactivex.Observable

interface QuickDecisionsContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun bootstrap()

        fun getQuickDecisionResult(quickDecision: QuickDecision)

        fun addNewQuickDecision()

        fun addGroupToQuickDecisions(group: Group)
    }

    interface View : BaseContract.View {
        fun onDataLoaded(quickDecisions: List<QuickDecision>)

        fun onQuickDecisionResult(result: String, isOdd: Boolean)

        fun onDisplayUserGroups(groups: List<Group>)

        fun onGroupCreationRequired()

        fun onQuickDecisionsUpdated(quickDecisions: List<QuickDecision>)
    }

    interface ReactiveView : BaseContract.View {
        // Produces
        fun getQuickDecisionResult(): Observable<QuickDecision>

        fun addNewQuickDecision(): Observable<AddNewPlaceholder>

        // Consumes
        fun onDataLoaded(quickDecisions: List<QuickDecision>)

        fun onQuickDecisionResult(result: String, isOdd: Boolean)

        fun onDisplayUserGroups(groups: List<Group>)

        fun onGroupCreationRequired()

        fun onQuickDecisionsUpdated(quickDecisions: List<QuickDecision>)
    }
}
