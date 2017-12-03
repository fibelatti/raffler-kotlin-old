package com.fibelatti.raffler.di.module

import com.fibelatti.raffler.presentation.base.AppSchedulerProvider
import com.fibelatti.raffler.presentation.base.SchedulerProvider
import com.fibelatti.raffler.presentation.home.NavigationContract
import com.fibelatti.raffler.presentation.home.NavigationPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    fun provideNavigationPresenter(schedulerProvider: SchedulerProvider): NavigationContract.Presenter = NavigationPresenter(schedulerProvider)
}
