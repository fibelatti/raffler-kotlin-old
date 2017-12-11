package com.fibelatti.raffler.presentation.quickdecisions

import com.fibelatti.raffler.domain.group.GetGroupsUseCase
import com.fibelatti.raffler.domain.quickdecision.AddGroupAsQuickDecisionUseCase
import com.fibelatti.raffler.domain.quickdecision.GetQuickDecisionsUseCase
import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.common.SchedulerProvider
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*

class QuickDecisionsPresenter(schedulerProvider: SchedulerProvider,
                              private val getQuickDecisionsUseCase: GetQuickDecisionsUseCase,
                              private val addGroupAsQuickDecisionUseCase: AddGroupAsQuickDecisionUseCase,
                              private val getGroupsUseCase: GetGroupsUseCase
) : QuickDecisionsContract.Presenter, BasePresenter<QuickDecisionsContract.View>(schedulerProvider) {
    override fun bootstrap() {
        view?.showProgress()

        val quickDecisionsSingle = getQuickDecisionsUseCase.getAllQuickDecisions()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())

        val groupsSingle = getGroupsUseCase.getAllGroups()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())

        Single.zip(
                quickDecisionsSingle,
                groupsSingle,
                BiFunction { quickDecisions: List<QuickDecision>, groups: List<Group> ->
                    Pair(quickDecisions, groups)
                })
                .subscribe(
                        { pair ->
                            view?.hideProgress()
                            view?.onDataLoaded(pair.first, pair.second)
                        },
                        ::handleError
                )
    }

    override fun getQuickDecisionResult(quickDecision: QuickDecision) {
        val randomIndex = Random().nextInt(quickDecision.items.size)
        val isOdd = randomIndex and 0x01 != 0

        view?.onQuickDecisionResult(quickDecision.items[(randomIndex)], isOdd)
    }

    override fun addGroupToQuickDecisions(group: Group) {
        view?.showProgress()

        addGroupAsQuickDecisionUseCase.addGroupAsQuickDecision(group)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::handleAddGroupsAsQuickDecisionSuccess,
                        ::handleError
                )
    }

    private fun handleAddGroupsAsQuickDecisionSuccess(result: Boolean) {
        when (result) {
            true -> getUpdatedQuickDecisions()
            false -> handleError()
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
