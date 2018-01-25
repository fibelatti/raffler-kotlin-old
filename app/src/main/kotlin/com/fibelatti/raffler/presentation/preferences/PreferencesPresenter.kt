package com.fibelatti.raffler.presentation.preferences

import com.fibelatti.raffler.domain.preferences.GetPreferencesUseCase
import com.fibelatti.raffler.domain.preferences.UpdatePreferencesUseCase
import com.fibelatti.raffler.presentation.base.BasePresenter
import com.fibelatti.raffler.presentation.common.SchedulerProvider
import com.fibelatti.raffler.presentation.models.Preferences
import com.fibelatti.raffler.presentation.preferences.PreferencesContract.View

class PreferencesPresenter(
    schedulerProvider: SchedulerProvider,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val updatePreferencesUseCase: UpdatePreferencesUseCase
) : PreferencesContract.Presenter, BasePresenter<PreferencesContract.View>(schedulerProvider) {
    override fun bind(view: View) {
        super.bind(view)

        view.showProgress()

        getPreferencesUseCase.getPreferences()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribeUntilDetached(
                { handleGetSuccess(view, preferences = it) },
                { view.handleError(errorMessage = it.message) }
            )

        view.updatePreferences()
            .getObservable()
            .subscribeUntilDetached { updatePreferences(view, preferences = it) }
    }

    private fun updatePreferences(view: PreferencesContract.View, preferences: Preferences) {
        updatePreferencesUseCase.updatePreferences(preferences)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribeUntilDetached(
                { handleUpdateSuccess(view) },
                { view.handleError(errorMessage = it.message) }
            )
    }

    private fun handleGetSuccess(view: PreferencesContract.View, preferences: Preferences) {
        view.hideProgress()
        view.onPreferencesFetched(preferences)
    }

    private fun handleUpdateSuccess(view: PreferencesContract.View) {
        view.onPreferencesUpdated()
    }
}
