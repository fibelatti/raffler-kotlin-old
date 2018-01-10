package com.fibelatti.raffler.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.data.localdatastorage.DATABASE_NAME
import com.fibelatti.raffler.data.localdatastorage.UserPreferencesContract
import com.fibelatti.raffler.data.localdatastorage.UserPreferencesManager
import com.fibelatti.raffler.data.preferences.PreferencesRepository
import com.fibelatti.raffler.data.preferences.PreferencesRepositoryContract
import com.fibelatti.raffler.domain.group.*
import com.fibelatti.raffler.domain.preferences.GetPreferencesUseCase
import com.fibelatti.raffler.domain.preferences.UpdatePreferencesUseCase
import com.fibelatti.raffler.domain.quickdecision.AddGroupAsQuickDecisionUseCase
import com.fibelatti.raffler.domain.quickdecision.GetQuickDecisionsUseCase
import com.fibelatti.raffler.presentation.common.AppSchedulerProvider
import com.fibelatti.raffler.presentation.common.DialogHelper
import com.fibelatti.raffler.presentation.common.SchedulerProvider
import com.fibelatti.raffler.presentation.home.NavigationContract
import com.fibelatti.raffler.presentation.home.NavigationPresenter
import com.fibelatti.raffler.presentation.preferences.PreferencesContract
import com.fibelatti.raffler.presentation.preferences.PreferencesPresenter
import com.fibelatti.raffler.presentation.quickdecisions.QuickDecisionsContract
import com.fibelatti.raffler.presentation.quickdecisions.QuickDecisionsPresenter
import com.fibelatti.raffler.presentation.quickdecisions.adapter.AddNewDelegateAdapter
import com.fibelatti.raffler.presentation.quickdecisions.adapter.QuickDecisionDelegateAdapter
import com.fibelatti.raffler.presentation.quickdecisions.adapter.QuickDecisionsAdapter
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import java.util.*

const val APP_CONTEXT = "AppContext"
const val APP_SHARED_PREFERENCES = "AppSharedPreferences"
const val DATABASE = "Database"
const val DEFAULT_SHARED_PREFERENCES = "DefaultSharedPreferences"
const val DIALOG_BUILDER = "DialogBuilder"
const val LOCALE_DEFAULT = "LocaleDefault"
const val USER_PREFERENCES = "UserPreferences"

val appModule = applicationContext {
    provide { androidApplication() }
    provide(name = APP_CONTEXT) { get<Application>().applicationContext }
    provide(name = DEFAULT_SHARED_PREFERENCES) { get<Context>(APP_CONTEXT).getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE) as SharedPreferences }
    provide(name = USER_PREFERENCES) { UserPreferencesManager(get(DEFAULT_SHARED_PREFERENCES)) as UserPreferencesContract }
    provide(name = DATABASE) {
        Room.databaseBuilder(get(APP_CONTEXT), AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(AppDatabase.MigrationTo4, AppDatabase.MigrationTo5)
            .addCallback(AppDatabase.RoomCallback)
            .fallbackToDestructiveMigration()
            .build()
    }
    provide(name = LOCALE_DEFAULT) { Locale.getDefault() }
}

val repositoryModule = applicationContext {
    provide { PreferencesRepository(get(USER_PREFERENCES)) as PreferencesRepositoryContract }
}

val useCaseModule = applicationContext {
    factory { DeleteGroupItemUseCase(get(DATABASE)) }
    factory { DeleteGroupUseCase(get(DATABASE)) }
    factory { GetGroupItemsUseCase(get(DATABASE)) }
    factory { GetGroupsUseCase(get(DATABASE)) }
    factory { SaveGroupUseCase(get(DATABASE)) }

    factory { GetPreferencesUseCase(get()) }
    factory { UpdatePreferencesUseCase(get()) }

    factory { AddGroupAsQuickDecisionUseCase(get(DATABASE)) }
    factory { GetQuickDecisionsUseCase(get(DATABASE), get(LOCALE_DEFAULT)) }
}

val presenterModule = applicationContext {
    factory { AppSchedulerProvider() as SchedulerProvider }
    factory { NavigationPresenter(get()) as NavigationContract.Presenter }
    factory { QuickDecisionsPresenter(get(), get(), get(), get()) as QuickDecisionsContract.Presenter }
    factory { PreferencesPresenter(get(), get(), get()) as PreferencesContract.Presenter }
}

val adapterModule = applicationContext {
    factory { QuickDecisionsAdapter(get(), get()) }
    factory { QuickDecisionDelegateAdapter() }
    factory { AddNewDelegateAdapter() }
}

val uiModule = applicationContext {
    factory { DialogHelper(get(DIALOG_BUILDER)) }
}

val androidFrameworkModule = applicationContext {
    factory(name = DIALOG_BUILDER) { AlertDialog.Builder(get(APP_CONTEXT)) }
}

val allModules = listOf(
    appModule,
    repositoryModule,
    useCaseModule,
    presenterModule,
    adapterModule,
    uiModule,
    androidFrameworkModule
)
