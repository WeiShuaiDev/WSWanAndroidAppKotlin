package com.linwei.cams.framework.mvi.ktx

import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.linwei.cams.framework.mvi.mvi.intent.livedata.LiveDataListEvent
import com.linwei.cams_mvvm.livedatabus.LiveDataEvent
import kotlin.reflect.KProperty1

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

/**
 * distinctUntilChanged 在更改此 LiveData 值的源之前，新的 LiveData 对象不会发出值。
 * 如果 equals() 产生 false，则认为该值已更改。
 */
fun <T, A> LiveData<T>.observeOnlyState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    action: (A) -> Unit
) {
    this.map {
        StateTuple1(prop1.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner) { (a) ->
        action.invoke(a)
    }
}

fun <T, A> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    action: (A) -> Unit
) {
    this.map {
        StateTuple1(prop1.get(it))
    }.observe(lifecycleOwner) { (a) ->
        action.invoke(a)
    }
}

fun <T, A, B> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    action: (A, B) -> Unit
) {
    this.map {
        StateTuple2(prop1.get(it), prop2.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner) { (a, b) ->
        action.invoke(a, b)
    }
}

fun <T, A, B, C> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    prop3: KProperty1<T, C>,
    action: (A, B, C) -> Unit
) {
    this.map {
        StateTuple3(prop1.get(it), prop2.get(it), prop3.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner) { (a, b, c) ->
        action.invoke(a, b, c)
    }
}

internal data class StateTuple1<A>(val a: A)
internal data class StateTuple2<A, B>(val a: A, val b: B)
internal data class StateTuple3<A, B, C>(val a: A, val b: B, val c: C)

fun <T> MutableLiveData<T>.setState(reducer: T.() -> T) {
    this.value = this.value?.reducer()
}

fun <T> LiveDataListEvent<T>.setState(reducer: List<T>.() -> List<T>) {
    this.value = this.value?.reducer()
}

fun <T> LiveDataListEvent<T>.setPostState(reducer: List<T>.() -> List<T>) {
    this.postValue(this.value?.reducer())
}

fun <T> LiveDataEvent<T>.setState(reducer: T.() -> T) {
    this.value = this.value?.reducer()
}

fun <T> LiveDataEvent<T>.setPostState(reducer: T.() -> T) {
    this.postValue(this.value?.reducer())
}

fun <T> LiveDataEvent<T>.setEvent(value: T) {
    this.value = value
}

fun <T> LiveDataEvent<T>.setPostEvent(value: T) {
    this.postValue(value)
}

fun <T> LiveDataListEvent<T>.setEvent(values: List<T>) {
    this.value = values
}

fun <T> LiveDataListEvent<T>.setPostEvent(values: List<T>) {
    this.postValue(values)
}

fun <T> LiveData<List<T>>.observeEvent(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    this.observe(lifecycleOwner) {
        it.forEach { event ->
            action.invoke(event)
        }
    }
}

fun <T, R> withState(state: LiveData<T>, block: (T) -> R): R? {
    return state.value?.let(block)
}