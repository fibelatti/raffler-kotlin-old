package com.fibelatti.raffler.di.module

import com.fibelatti.raffler.domain.group.GetGroupsUseCase
import com.fibelatti.raffler.domain.preferences.GetPreferencesUseCase
import com.fibelatti.raffler.domain.preferences.UpdatePreferencesUseCase
import com.fibelatti.raffler.domain.quickdecision.AddGroupAsQuickDecisionUseCase
import com.fibelatti.raffler.domain.quickdecision.GetQuickDecisionsUseCase
import com.fibelatti.raffler.presentation.base.BaseContract
import com.fibelatti.raffler.presentation.common.AppSchedulerProvider
import com.fibelatti.raffler.presentation.common.SchedulerProvider
import com.fibelatti.raffler.presentation.home.NavigationContract
import com.fibelatti.raffler.presentation.home.NavigationPresenter
import com.fibelatti.raffler.presentation.preferences.PreferencesContract
import com.fibelatti.raffler.presentation.preferences.PreferencesPresenter
import com.fibelatti.raffler.presentation.quickdecisions.QuickDecisionsPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    fun provideNavigationPresenter(schedulerProvider: SchedulerProvider):
        NavigationContract.Presenter = NavigationPresenter(schedulerProvider)

    @Provides
    fun provideQuickDecisionsPresenter(schedulerProvider: SchedulerProvider,
                                       getQuickDecisionsUseCase: GetQuickDecisionsUseCase,
                                       addGroupAsQuickDecisionUseCase: AddGroupAsQuickDecisionUseCase,
                                       getGroupsUseCase: GetGroupsUseCase):
        BaseContract.ReactivePresenter = QuickDecisionsPresenter(schedulerProvider, getQuickDecisionsUseCase, addGroupAsQuickDecisionUseCase, getGroupsUseCase)

    @Provides
    fun providePreferencesPresenter(schedulerProvider: SchedulerProvider,
                                    getPreferencesUseCase: GetPreferencesUseCase,
                                    updatePreferencesUseCase: UpdatePreferencesUseCase):
        PreferencesContract.Presenter = PreferencesPresenter(schedulerProvider, getPreferencesUseCase, updatePreferencesUseCase)
}
