package com.fibelatti.raffler.presentation.preferences

import com.fibelatti.raffler.core.ifNotNullThisElseThat
import com.fibelatti.raffler.domain.preferences.GetPreferencesUseCase
import com.fibelatti.raffler.domain.preferences.UpdatePreferencesUseCase
import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.base.SchedulerProvider
import com.fibelatti.raffler.presentation.models.Preferences


class PreferencesPresenter(
        schedulerProvider: SchedulerProvider,
        private val getPreferencesUseCase: GetPreferencesUseCase,
        private val updatePreferencesUseCase: UpdatePreferencesUseCase
) : PreferencesContract.Presenter, BasePresenter<PreferencesContract.View>(schedulerProvider) {
    override fun getPreferences() {
        view?.showProgress()

        getPreferencesUseCase.getPreferences()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::handleGetSuccess,
                        ::handleError
                )
    }

    override fun updatePreferences(preferences: Preferences) {
        updatePreferencesUseCase.updatePreferences(preferences)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::handleUpdateSuccess,
                        ::handleError
                )

    }

    private fun handleGetSuccess(preferences: Preferences?) {
        view?.hideProgress()
        preferences.ifNotNullThisElseThat({ view?.onPreferencesFetched(it) }, { view?.handleError(null) })
    }

    private fun handleError(error: Throwable) {
        view?.hideProgress()
        view?.handleError(error.message)
    }

    private fun handleUpdateSuccess(success: Boolean) {
        if (success) {
            view?.onPreferencesUpdated()
        } else {
            view?.handleError(null)
        }
    }
}
