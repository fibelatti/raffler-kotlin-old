package com.fibelatti.raffler.presentation.quickdecisions

import com.fibelatti.raffler.domain.group.GetGroupsUseCase
import com.fibelatti.raffler.domain.quickdecision.AddGroupAsQuickDecisionUseCase
import com.fibelatti.raffler.domain.quickdecision.GetQuickDecisionsUseCase
import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.common.SchedulerProvider
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision
import java.util.*

class QuickDecisionsPresenter(schedulerProvider: SchedulerProvider,
                              private val getQuickDecisionsUseCase: GetQuickDecisionsUseCase,
                              private val addGroupAsQuickDecisionUseCase: AddGroupAsQuickDecisionUseCase,
                              private val getGroupsUseCase: GetGroupsUseCase
) : QuickDecisionsContract.Presenter, BasePresenter<QuickDecisionsContract.View>(schedulerProvider) {
    override fun bootstrap() {
        view?.showProgress()

        getQuickDecisionsUseCase.getAllQuickDecisions()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::handleGetAllQuickDecisionsSuccess,
                        ::handleError
                )
    }

    override fun getQuickDecisionResult(quickDecision: QuickDecision) {
        val randomIndex = Random().nextInt(quickDecision.items.size)
        val isOdd = randomIndex and 0x01 != 0

        view?.onQuickDecisionResult(quickDecision.items[(randomIndex)], isOdd)
    }

    override fun addNewQuickDecision() {
        getGroupsUseCase.getAllGroups()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::handleGetAllGroupsSuccess,
                        ::handleError
                )
    }

    override fun addGroupToQuickDecisions(group: Group) {
        view?.showProgress()

        addGroupAsQuickDecisionUseCase.addGroupAsQuickDecision(group)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::getUpdatedQuickDecisions,
                        ::handleError
                )
    }

    private fun handleGetAllQuickDecisionsSuccess(quickDecisions: List<QuickDecision>) {
        view?.hideProgress()
        view?.onDataLoaded(quickDecisions)
    }

    private fun handleGetAllGroupsSuccess(groups: List<Group>) {
        when (groups.size) {
            0 -> view?.onGroupCreationRequired()
            else -> view?.onDisplayUserGroups(groups)
        }
    }

    private fun getUpdatedQuickDecisions() {
        getQuickDecisionsUseCase.getAllQuickDecisions()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::handleGetUpdatedQuickDecisionsSuccess,
                        ::handleError
                )
    }

    private fun handleGetUpdatedQuickDecisionsSuccess(quickDecisions: List<QuickDecision>) {
        view?.hideProgress()
        view?.onQuickDecisionsUpdated(quickDecisions)
    }
}
