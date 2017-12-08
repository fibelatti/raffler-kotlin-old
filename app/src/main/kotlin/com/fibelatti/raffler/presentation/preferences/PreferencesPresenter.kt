package com.fibelatti.raffler.presentation.preferences

import com.fibelatti.raffler.core.extensions.dropBreadcrumb
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
                .dropBreadcrumb()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::handleGetSuccess,
                        ::handleError
                )
    }

    override fun updatePreferences(preferences: Preferences) {
        updatePreferencesUseCase.updatePreferences(preferences)
                .dropBreadcrumb()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        ::handleUpdateSuccess,
                        ::handleError
                )

    }

    private fun handleGetSuccess(preferences: Preferences) {
        view?.hideProgress()
        view?.onPreferencesFetched(preferences)
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
