package com.mehyo.postsapp.repository

import com.mehyo.postsapp.model.Post
import com.mehyo.postsapp.network.PostsAPI
import retrofit2.Response

class NetworkRepository(private val api: PostsAPI) {

    /**
     * suspending function to
     * get Posts from API.
     * @return all posts.
     */
    suspend fun getPosts(): Response<List<Post>> {
        return api.getPosts()
    }

    /**
     * suspending function to
     * delete a post by id from API
     * @return an emptyList.
     */
    suspend fun deletePostById(id: Int): Response<Any?> {
        return api.deletePostById(id)
    }

    /**
     * suspending function to
     * add a new post to API.
     * @return the newly created post.
     */
    suspend fun addPost(body: Post): Response<Post> {
        return api.addPost(body)
    }

    /**
     * suspending function to
     * edit a post by id from API
     * @return the edited post.
     */
    suspend fun editPostById(body: Post, id: Int): Response<Post> {
        return api.editPostById(body, id)
    }

    /**
     * suspending function to
     * get a Post by id From API.
     * @return the requested post details.
     */
    suspend fun getPostById(id: Int): Response<Post> {
        return api.getPostById(id)
    }
}