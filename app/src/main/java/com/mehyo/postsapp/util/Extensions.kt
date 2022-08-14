package com.mehyo.postsapp.util

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.mehyo.postsapp.network.Resource
import com.mehyo.postsapp.network.ResourceState

/**
 * View extension function to
 * set the visibility of the view to VISIBLE.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * View extension function to
 * set the visibility of the view to GONE.
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * MutableLiveData extension function to
 * post the Resource value in the success state.
 */
fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T, message: String? = null) =
    postValue(
        Resource(
            ResourceState.SUCCESS,
            data,
            message
        )
    )

/**
 * MutableLiveData extension function to
 * post the Resource value in the loading state.
 */
fun <T> MutableLiveData<Resource<T>>.setLoading() =
    postValue(
        Resource(
            ResourceState.LOADING,
            value?.data
        )
    )

/**
 * MutableLiveData extension function to
 * post the Resource value in the error state.
 */
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
