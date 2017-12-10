package com.fibelatti.raffler.presentation.home

import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.common.SchedulerProvider

class NavigationPresenter(schedulerProvider: SchedulerProvider) :
        BasePresenter<NavigationContract.View>(schedulerProvider),
        NavigationContract.Presenter {

    override fun onQuickDecisionsClicked() {
        view?.goToQuickDecisions()
    }

    override fun onGroupsClicked() {
        view?.goToGroups()
    }

    override fun onPreferencesClicked() {
        view?.goToPreferences()
    }
}
