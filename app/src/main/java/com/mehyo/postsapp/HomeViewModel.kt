package com.mehyo.postsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    private val postsResult = MutableLiveData<Resource<List<Post>>>()
    val postsResultLiveData: LiveData<Resource<List<Post>>> get() = postsResult

    private suspend fun getPosts() {
        postsResult.setLoading()
        try {
            networkRepository.getPosts().body()?.let { listPostsData ->
                postsResult.setSuccess(data = listPostsData)
            }
        } catch (throwable: Throwable) {
            postsResult.setError(throwable.message, Exception(throwable))
        }
    }

    fun getPostsAsync() {
        launchAsync { getPosts() }
    }

    private fun launchAsync(method: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                method()
            }
        }
    }

}