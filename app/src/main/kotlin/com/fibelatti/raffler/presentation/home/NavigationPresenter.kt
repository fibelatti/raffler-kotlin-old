package com.fibelatti.raffler.presentation.home

import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.common.SchedulerProvider
import com.fibelatti.raffler.presentation.home.NavigationContract.View

class NavigationPresenter(schedulerProvider: SchedulerProvider) :
    BasePresenter<NavigationContract.View>(schedulerProvider),
    NavigationContract.Presenter {

    override fun bind(view: View) {
        super.bind(view)

        view.quickDecisionsClicked()
            .getObservable()
            .subscribeUntilDetached { view.goToQuickDecisions() }
        view.groupsClicked()
            .getObservable()
            .subscribeUntilDetached { view.goToGroups() }
        view.preferencesClicked()
            .getObservable()
            .subscribeUntilDetached { view.goToPreferences() }
    }
}
