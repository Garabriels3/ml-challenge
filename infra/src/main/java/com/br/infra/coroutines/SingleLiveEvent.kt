package com.br.infra.coroutines

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEvent<T> : LiveData<T> {
    constructor() : super()
    constructor(value: T) : super(value)

    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    protected open fun emit(value: T) {
        pending.set(true)
        super.setValue(value)
    }

    protected open fun post(value: T) {
        pending.set(true)
        super.postValue(value)
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}