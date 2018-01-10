package com.fibelatti.raffler

import android.app.Application
import com.fibelatti.raffler.di.allModules
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, allModules)
    }
}
