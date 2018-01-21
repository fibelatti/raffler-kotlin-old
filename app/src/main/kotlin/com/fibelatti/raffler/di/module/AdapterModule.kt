package com.fibelatti.raffler.di.module

import com.fibelatti.raffler.presentation.quickdecisions.adapter.AddNewDelegateAdapter
import com.fibelatti.raffler.presentation.quickdecisions.adapter.QuickDecisionDelegateAdapter
import com.fibelatti.raffler.presentation.quickdecisions.adapter.QuickDecisionsAdapter
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {
    @Provides
    fun provideQuickDecisionsAdapter(quickDecisionDelegateAdapter: QuickDecisionDelegateAdapter,
                                     addNewDelegateAdapter: AddNewDelegateAdapter) =
        QuickDecisionsAdapter(quickDecisionDelegateAdapter, addNewDelegateAdapter)

    @Provides
    fun provideQuickDecisionDelegateAdapter() = QuickDecisionDelegateAdapter()

    @Provides
    fun provideAddNewDelegateAdapter() = AddNewDelegateAdapter()
}
