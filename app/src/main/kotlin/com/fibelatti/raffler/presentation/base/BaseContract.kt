package com.fibelatti.raffler.presentation.base

import android.widget.FrameLayout

interface BaseContract {
    interface View {
        val rootLayout: FrameLayout?

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
