package com.fibelatti.raffler.di.module

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fibelatti.raffler.App
import com.fibelatti.raffler.core.UserPreferencesContract
import com.fibelatti.raffler.core.UserPreferencesManager
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
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

    @Provides
    @AppScope
    fun provideUserPreferences(sharedPreferences: SharedPreferences): UserPreferencesContract = UserPreferencesManager(sharedPreferences)

    @Provides
    @AppScope
    fun providesDatabase(@AppQualifier context: Context): AppDatabase {
        return Room.databaseBuilder(context,
                AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .addMigrations(AppDatabase.MigrationTo4, AppDatabase.MigrationTo5)
                .addCallback(AppDatabase.RoomCallback)
                .fallbackToDestructiveMigration()
                .build()
    }
}
