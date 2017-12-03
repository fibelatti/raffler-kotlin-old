package com.fibelatti.raffler.presentation.base

abstract class BasePresenter<V : BaseContract.View>(protected val schedulerProvider: SchedulerProvider) : BaseContract.Presenter<V> {
    protected var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}
