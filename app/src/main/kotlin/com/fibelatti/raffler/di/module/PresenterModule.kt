package com.fibelatti.raffler.di.module

import com.fibelatti.raffler.domain.preferences.GetPreferencesUseCase
import com.fibelatti.raffler.domain.preferences.UpdatePreferencesUseCase
import com.fibelatti.raffler.presentation.base.AppSchedulerProvider
import com.fibelatti.raffler.presentation.base.SchedulerProvider
import com.fibelatti.raffler.presentation.home.NavigationContract
import com.fibelatti.raffler.presentation.home.NavigationPresenter
import com.fibelatti.raffler.presentation.preferences.PreferencesContract
import com.fibelatti.raffler.presentation.preferences.PreferencesPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    fun provideNavigationPresenter(schedulerProvider: SchedulerProvider): NavigationContract.Presenter
            = NavigationPresenter(schedulerProvider)

    @Provides
    fun providePreferencesPresenter(schedulerProvider: SchedulerProvider,
                                    getPreferencesUseCase: GetPreferencesUseCase,
                                    updatePreferencesUseCase: UpdatePreferencesUseCase): PreferencesContract.Presenter
            = PreferencesPresenter(schedulerProvider, getPreferencesUseCase, updatePreferencesUseCase)
}
