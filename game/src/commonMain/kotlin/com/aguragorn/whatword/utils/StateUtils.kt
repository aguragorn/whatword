package com.aguragorn.whatword.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.reflect.KProperty

class MutableState<T>(initial: T) {
    private val innerState = MutableStateFlow(initial)
    private val innerStateFlow = innerState.asStateFlow()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): StateFlow<T> {
        return innerStateFlow
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        innerState.value = value
    }
}

