package com.fibelatti.raffler.presentation.preferences

import com.fibelatti.raffler.domain.preferences.GetPreferencesUseCase
import com.fibelatti.raffler.domain.preferences.UpdatePreferencesUseCase
import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.common.SchedulerProvider
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

    private fun handleGetSuccess(preferences: Preferences) {
        view?.hideProgress()
        view?.onPreferencesFetched(preferences)
    }

    private fun handleUpdateSuccess() {
        view?.onPreferencesUpdated()
    }
}
