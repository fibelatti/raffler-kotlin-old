package com.fibelatti.raffler.di.component

import android.content.SharedPreferences
import com.fibelatti.raffler.di.module.ActivityModule
import com.fibelatti.raffler.di.module.AppModule
import com.fibelatti.raffler.di.module.PresenterModule
import com.fibelatti.raffler.di.module.RepositoryModule
import com.fibelatti.raffler.di.scope.AppScope
import dagger.Component

@Component(
    modules = [AppModule::class, RepositoryModule::class]
)
@AppScope
interface AppComponent {
    fun plus(activityModule: ActivityModule, presenterModule: PresenterModule): PresentationComponent

    fun sharedPreferences(): SharedPreferences
}
