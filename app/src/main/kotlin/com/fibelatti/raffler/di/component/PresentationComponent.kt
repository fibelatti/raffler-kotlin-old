package com.fibelatti.raffler.di.component

import com.fibelatti.raffler.di.module.ActivityModule
import com.fibelatti.raffler.di.module.PresenterModule
import com.fibelatti.raffler.di.scope.PresentationScope
import com.fibelatti.raffler.presentation.base.BaseActivity
import com.fibelatti.raffler.presentation.home.HomeActivity
import com.fibelatti.raffler.presentation.preferences.PreferencesFragment
import dagger.Subcomponent

@Subcomponent(
        modules = [ActivityModule::class, PresenterModule::class]
)
@PresentationScope
interface PresentationComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(homeActivity: HomeActivity)

    fun inject(preferencesFragment: PreferencesFragment)
}
