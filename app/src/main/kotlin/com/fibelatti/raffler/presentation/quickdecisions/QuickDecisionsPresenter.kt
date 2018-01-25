package com.fibelatti.raffler.presentation.quickdecisions

import com.fibelatti.raffler.domain.group.GetGroupsUseCase
import com.fibelatti.raffler.domain.quickdecision.AddGroupAsQuickDecisionUseCase
import com.fibelatti.raffler.domain.quickdecision.GetQuickDecisionsUseCase
import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.common.SchedulerProvider
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision
import java.util.Random

class QuickDecisionsPresenter(schedulerProvider: SchedulerProvider,
                              private val getQuickDecisionsUseCase: GetQuickDecisionsUseCase,
                              private val addGroupAsQuickDecisionUseCase: AddGroupAsQuickDecisionUseCase,
                              private val getGroupsUseCase: GetGroupsUseCase
) : QuickDecisionsContract.Presenter, BasePresenter<QuickDecisionsContract.View>(schedulerProvider) {
    override fun bind(view: QuickDecisionsContract.View) {
        super.bind(view)
        view.showProgress()

        getQuickDecisionsUseCase.getAllQuickDecisions()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doFinally({ view.hideProgress() })
            .subscribeUntilDetached(
                { view.onDataLoaded(quickDecisions = it) },
                { view.handleError(errorMessage = it.message) }
            )

        view.quickDecisionClicked()
            .getObservable()
            .subscribeUntilDetached({ getQuickDecisionResult(view, quickDecision = it) })
        view.addNewClicked()
            .getObservable()
            .subscribeUntilDetached({ addNewQuickDecision(view) })
        view.createGroup()
            .getObservable()
            .subscribeUntilDetached { addGroupToQuickDecisions(view, group = it) }
    }

    private fun getQuickDecisionResult(view: QuickDecisionsContract.View, quickDecision: QuickDecision) {
        val randomIndex = Random().nextInt(quickDecision.items.size)
        val isOdd = randomIndex and 0x01 != 0

        view.onQuickDecisionResult(quickDecision.items[(randomIndex)], isOdd)
    }

    private fun addNewQuickDecision(view: QuickDecisionsContract.View) {
        getGroupsUseCase.getAllGroups()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribeUntilDetached(
                {
                    when (it.size) {
                        0 -> view.onGroupCreationRequired()
                        else -> view.onDisplayUserGroups(it)
                    }
                },
                { view.handleError(it.message) }
            )
    }

    private fun addGroupToQuickDecisions(view: QuickDecisionsContract.View, group: Group) {
        view.showProgress()

        addGroupAsQuickDecisionUseCase.addGroupAsQuickDecision(group)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doFinally({ view.hideProgress() })
            .subscribeUntilDetached(
                { getUpdatedQuickDecisions(view) },
                { view.handleError(it.message) }
            )
    }

    private fun getUpdatedQuickDecisions(view: QuickDecisionsContract.View) {
        view.showProgress()

        getQuickDecisionsUseCase.getAllQuickDecisions()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doFinally({ view.hideProgress() })
            .subscribeUntilDetached(
                { handleGetUpdatedQuickDecisionsSuccess(view, quickDecisions = it) },
                { view.handleError(it.message) }
            )
    }

    private fun handleGetUpdatedQuickDecisionsSuccess(view: QuickDecisionsContract.View, quickDecisions: List<QuickDecision>) {
        view.onQuickDecisionsUpdated(quickDecisions)
    }
}
