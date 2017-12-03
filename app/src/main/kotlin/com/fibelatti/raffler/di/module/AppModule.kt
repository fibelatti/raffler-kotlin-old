package com.fibelatti.raffler.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fibelatti.raffler.App
import com.fibelatti.raffler.di.qualifier.AppQualifier
import com.fibelatti.raffler.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private var app: App) {

    @Provides
    @AppScope
    fun provideApp(): App = app

    @Provides
    @AppScope
    @AppQualifier
    fun provideContext(): Context = app.applicationContext

    @Provides
    @AppScope
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)
}
