package com.fibelatti.raffler.di.module

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: FragmentActivity) {

    @Provides
    fun provideContext(): Context = activity.baseContext

    @Provides
    fun provideActivity(): FragmentActivity = activity

    @Provides
    fun provideDialogBuilder(context: Context): AlertDialog.Builder = AlertDialog.Builder(context)
}
