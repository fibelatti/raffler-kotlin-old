package com.fibelatti.raffler.di.module

import com.fibelatti.raffler.presentation.base.AppSchedulerProvider
import com.fibelatti.raffler.presentation.base.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}
