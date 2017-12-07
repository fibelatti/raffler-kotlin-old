package com.fibelatti.raffler

import io.reactivex.observers.TestObserver

abstract class BaseTest {
    protected val testSchedulerProvider = TestSchedulerProvider()

    protected fun <T> assertCompleteAndNoErrors(testObserver: TestObserver<T>) {
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
    }
}
