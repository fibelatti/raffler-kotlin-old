package com.fibelatti.raffler.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.with
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.KoinTest
import org.koin.test.dryRun
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class DryRunTest : KoinTest {
    private val mockApplication = mock(Application::class.java)
    private val mockContext = mock(Context::class.java)
    private val mockSharedPreferences = mock(SharedPreferences::class.java)

    private val mockAndroidFrameworkModule = applicationContext {
        factory(name = DIALOG_BUILDER) { mock(AlertDialog.Builder::class.java) }
    }

    private val testableModule = listOf(
        appModule,
        repositoryModule,
        useCaseModule,
        presenterModule,
        adapterModule,
        uiModule,
        mockAndroidFrameworkModule
    )

    @Before
    fun before() {
        given(mockApplication.applicationContext)
            .willReturn(mockContext)
        given(mockContext.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE))
            .willReturn(mockSharedPreferences)

        startKoin(testableModule) with mockApplication
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun dryRunTest() {
        dryRun()
    }
}
