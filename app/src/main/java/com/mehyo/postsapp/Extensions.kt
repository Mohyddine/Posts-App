package com.mehyo.postsapp

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.HttpException

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T, message: String? = null) =
    postValue(
        Resource(
            ResourceState.SUCCESS,
            data,
            message
        )
    )

fun <T> MutableLiveData<Resource<T>>.setLoading() =
    postValue(
        Resource(
            ResourceState.LOADING,
            value?.data
        )
    )

fun <T> MutableLiveData<Resource<T>>.setError(
    message: String? = null,
    exception: Exception? = null
) =
    postValue(
        Resource(
            ResourceState.ERROR,
            value?.data,
            message,
            exception
        )
    )

fun <T> MutableLiveData<Resource<T>>.setException(exception: Exception? = null) =
    postValue(
        Resource(
            ResourceState.ERROR,
            null,
            null,
            exception
        )
    )

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun <T> MutableLiveData<T>.postIfDifferent(newValue: T?) {
    if (newValue != value) {
        postValue(newValue)
    }
}

/**
 * Mediator which may observe multiple LiveData sources containing Resource type data
 * It combines and propagates theirs LOADING state
 *
 * It posts TRUE if ONE of the sources is in LOADING state
 * and posts FALSE if NONE of them are in LOADING state
 * */
fun MediatorLiveData<Boolean>.addLiveDataSources(vararg liveDataSources: LiveData<out Resource<Any>>) {
    for (liveData in liveDataSources) {
        addSource(liveData) { result ->
            postIfDifferent(
                if (result.state == ResourceState.LOADING) true
                else !liveDataSources.none { it.value?.state == ResourceState.LOADING }
            )
        }
    }
}

fun Resource<Any>.getMessage(defaultMessage: String): String {
    return this.message ?: defaultMessage
}

fun Resource<Any>.getHttpErrorCode(): Int? {
    return if (exception?.cause is HttpException) {
        (exception.cause as HttpException).code()
    } else {
        null
    }
}
