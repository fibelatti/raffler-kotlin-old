package com.fibelatti.raffler.presentation.base

import android.support.v4.app.FragmentActivity
import android.widget.FrameLayout
import com.fibelatti.raffler.App
import com.fibelatti.raffler.di.module.ActivityModule
import com.fibelatti.raffler.di.module.PresenterModule

interface BaseContract {
    interface View {
        val rootLayout: FrameLayout?

        fun getPresentationComponent(activity: FragmentActivity)
                = App.appComponent.plus(ActivityModule(activity), PresenterModule())

        fun showProgress()

        fun hideProgress()

        fun handleError(errorMessage: String?)

        fun onNetworkError()
    }

    interface Presenter<in V : View> {
        fun attachView(view: V)

        fun detachView()
    }
}
