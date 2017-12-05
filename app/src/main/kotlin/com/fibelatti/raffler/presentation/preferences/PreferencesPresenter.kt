package com.fibelatti.raffler.presentation.preferences

import com.fibelatti.raffler.core.ifNotNullThisElseThat
import com.fibelatti.raffler.domain.preferences.GetPreferencesUseCase
import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.base.SchedulerProvider
import com.fibelatti.raffler.presentation.models.Preferences


class PreferencesPresenter(schedulerProvider: SchedulerProvider, private val getPreferencesUseCase: GetPreferencesUseCase) :
    PreferencesContract.Presenter, BasePresenter<PreferencesContract.View>(schedulerProvider) {
    override fun getPreferences() {
        view?.showProgress()

        getPreferencesUseCase.getPreferences()
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        this::handleSuccess,
                        this::handleError
                )
    }

    private fun handleSuccess(preferences: Preferences?) {
        view?.hideProgress()
        preferences?.ifNotNullThisElseThat({view?.onPreferencesFetched(it)}, {view?.handleError(null)})
    }

    private fun handleError(error: Throwable) {
        view?.handleError(error.message)
    }
}
