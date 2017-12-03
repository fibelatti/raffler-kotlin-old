package com.fibelatti.raffler

import android.app.Application
import com.fibelatti.raffler.di.component.AppComponent
import com.fibelatti.raffler.di.component.DaggerAppComponent
import com.fibelatti.raffler.di.module.AppModule

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        buildComponents()
    }

    private fun buildComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
