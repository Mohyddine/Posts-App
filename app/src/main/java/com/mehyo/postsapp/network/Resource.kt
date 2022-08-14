package com.mehyo.postsapp.network


sealed class ResourceState {
    object LOADING : ResourceState()
    object SUCCESS : ResourceState()
    object ERROR : ResourceState()
}

data class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null
)
