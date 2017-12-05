package com.fibelatti.raffler.di.module

import com.fibelatti.raffler.core.UserPreferencesContract
import com.fibelatti.raffler.data.preferences.PreferencesRepository
import com.fibelatti.raffler.data.preferences.PreferencesRepositoryContract
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun providePreferencesRepository(userPreferencesContract: UserPreferencesContract): PreferencesRepositoryContract
            = PreferencesRepository(userPreferencesContract)
}
