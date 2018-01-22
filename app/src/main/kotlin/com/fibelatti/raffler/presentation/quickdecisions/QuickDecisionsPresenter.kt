package com.fibelatti.raffler.presentation.quickdecisions

import com.fibelatti.raffler.domain.group.GetGroupsUseCase
import com.fibelatti.raffler.domain.quickdecision.AddGroupAsQuickDecisionUseCase
import com.fibelatti.raffler.domain.quickdecision.GetQuickDecisionsUseCase
import com.fibelatti.raffler.presentation.base.BaseReactivePresenter
import com.fibelatti.raffler.presentation.common.SchedulerProvider
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision
import java.util.Random

class QuickDecisionsPresenter(schedulerProvider: SchedulerProvider,
                              private val getQuickDecisionsUseCase: GetQuickDecisionsUseCase,
                              private val addGroupAsQuickDecisionUseCase: AddGroupAsQuickDecisionUseCase,
                              private val getGroupsUseCase: GetGroupsUseCase
) : BaseReactivePresenter(schedulerProvider) {
    override fun <V> bind(view: V) {
        super.bind(view)
        when (view) {
            is QuickDecisionsContract.ReactiveView -> bindActivity(view)
        }
    }

    private fun bindActivity(view: QuickDecisionsContract.ReactiveView) {
        view.showProgress()

        getQuickDecisionsUseCase.getAllQuickDecisions()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doFinally({ view.hideProgress() })
            .subscribeUntilDetached(
                { view.onDataLoaded(quickDecisions = it) },
                { view.handleError(errorMessage = it.message) }
            )

        view.getQuickDecisionResult().subscribeUntilDetached({ getQuickDecisionResult(view, quickDecision = it) })
        view.addNewQuickDecision().subscribeUntilDetached({ addNewQuickDecision(view) })
    }

    private fun getQuickDecisionResult(view: QuickDecisionsContract.ReactiveView, quickDecision: QuickDecision) {
        val randomIndex = Random().nextInt(quickDecision.items.size)
        val isOdd = randomIndex and 0x01 != 0

        view.onQuickDecisionResult(quickDecision.items[(randomIndex)], isOdd)
    }

    private fun addNewQuickDecision(view: QuickDecisionsContract.ReactiveView) {
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

    fun addGroupToQuickDecisions(view: QuickDecisionsContract.ReactiveView, group: Group) {
        view.showProgress()

        addGroupAsQuickDecisionUseCase.addGroupAsQuickDecision(group)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribeUntilDetached(
                { getUpdatedQuickDecisions(view) },
                { view.handleError(it.message) }
            )
    }

    private fun getUpdatedQuickDecisions(view: QuickDecisionsContract.ReactiveView) {
        getQuickDecisionsUseCase.getAllQuickDecisions()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribeUntilDetached(
                { handleGetUpdatedQuickDecisionsSuccess(view, quickDecisions = it) },
                { view.handleError(it.message) }
            )
    }

    private fun handleGetUpdatedQuickDecisionsSuccess(view: QuickDecisionsContract.ReactiveView, quickDecisions: List<QuickDecision>) {
        view.hideProgress()
        view.onQuickDecisionsUpdated(quickDecisions)
    }
}
