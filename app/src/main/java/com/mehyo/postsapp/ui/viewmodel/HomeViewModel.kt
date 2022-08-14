package com.mehyo.postsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehyo.postsapp.model.Post
import com.mehyo.postsapp.network.Resource
import com.mehyo.postsapp.repository.NetworkRepository
import com.mehyo.postsapp.util.setError
import com.mehyo.postsapp.util.setLoading
import com.mehyo.postsapp.util.setSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    private val postsResult = MutableLiveData<Resource<List<Post>>>()
    val postsResultLiveData: LiveData<Resource<List<Post>>> get() = postsResult

    private var addedPost = MutableLiveData<Resource<Post>>()
    val addedPostLiveData: LiveData<Resource<Post>> get() = addedPost

    private var oldPost = MutableLiveData<Post?>(null)
    val oldPostLiveData: LiveData<Post?> get() = oldPost

    private var updatedPost = MutableLiveData<Resource<Post>>()
    val updatedPostLiveData: LiveData<Resource<Post>> get() = updatedPost

    private var deletePost = MutableLiveData<Resource<Any?>>()
    val deletePostLiveData: LiveData<Resource<Any?>> get() = deletePost

    /**
     * function to send old post from
     * bottomSheet to home screen.
     */
    fun sendOldPost(oldPost: Post) {
        this.oldPost.value = oldPost
    }

    /**
     * suspending function to get all posts
     * from the server, then saves the list
     * in a live data.
     */
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

    /**
     * suspending function to edit a post
     * using its id from the server,
     * then saves the edited post
     * in a live data.
     */
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

    /**
     * suspending function to add a new post
     * to the server, then saves
     * the edited post in a live data.
     */
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

    /**
     * suspending function to delete a post
     * from the server, then saves
     * an emptyList in a live data.
     */
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

    /**
     * function to run the getPosts function
     * asynchronously using kotlin coroutines.
     */
    fun getPostsAsync() {
        launchAsync { getPosts() }
    }

    /**
     * function to run the editPostById function
     * asynchronously using kotlin coroutines.
     */
    fun editPostByIdAsync(post: Post, id: Int) {
        launchAsync { editPostById(post, id) }
    }

    /**
     * function to run the addPost function
     * asynchronously using kotlin coroutines.
     */
    fun addPostAsync(post: Post) {
        launchAsync { addPost(post) }
    }

    /**
     * function to run the deletePost function
     * asynchronously using kotlin coroutines.
     */
    fun deletePostAsync(id: Int) {
        launchAsync { deletePost(id) }
    }

    /**
     * function to run the any suspending function
     * asynchronously using kotlin coroutines.
     */
    private fun launchAsync(method: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                method()
            }
        }
    }

}