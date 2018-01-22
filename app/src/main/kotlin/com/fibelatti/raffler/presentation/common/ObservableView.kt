package com.fibelatti.raffler.presentation.common

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ObservableView<Emits> {
    private val subject = BehaviorSubject.create<Emits>()

    fun getObservable(): Observable<Emits> = subject

    fun emitNext(value: Emits) = subject.onNext(value)
}
