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

    private var addedPost = MutableLiveData<Resource<Post>>()
    val addedPostLiveData: LiveData<Resource<Post>> get() = addedPost

    private var editedPost = MutableLiveData<Post?>(null)
    val editedPostLiveData: LiveData<Post?> get() = editedPost

    private var updatedPost = MutableLiveData<Resource<Post>>()
    val updatedPostLiveData: LiveData<Resource<Post>> get() = updatedPost

    private var deletePost = MutableLiveData<Resource<Any?>>()
    val deletePostLiveData: LiveData<Resource<Any?>> get() = deletePost

    fun editPost(post: Post) {
        editedPost.value = post
    }

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

    private suspend fun editPostById(post: Post, id: Int) {
        updatedPost.setLoading()
        try {
            networkRepository.editPostById(body = post, id = id).body()?.let { updatedPostData ->
                updatedPost.setSuccess(data = updatedPostData)
            }
        } catch (throwable: Throwable) {
            updatedPost.setError(throwable.message, Exception(throwable))
        }
    }

    private suspend fun addPost(post: Post) {
        addedPost.setLoading()
        try {
            networkRepository.addPost(body = post).body()?.let { addedPostData ->
                addedPost.setSuccess(data = addedPostData)
            }
        } catch (throwable: Throwable) {
            addedPost.setError(throwable.message, Exception(throwable))
        }
    }

    private suspend fun deletePost(id: Int) {
        addedPost.setLoading()
        try {
            networkRepository.deletePostById(id).body()?.let {
                deletePost.setSuccess(it)
            }
        } catch (throwable: Throwable) {
            deletePost.setError(throwable.message, Exception(throwable))
        }
    }

    fun getPostsAsync() {
        launchAsync { getPosts() }
    }

    fun editPostByIdAsync(post: Post, id: Int) {
        launchAsync { editPostById(post, id) }
    }

    fun addPostAsync(post: Post) {
        launchAsync { addPost(post) }
    }

    fun deletePostAsync(id: Int) {
        launchAsync { deletePost(id) }
    }

    private fun launchAsync(method: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                method()
            }
        }
    }

}