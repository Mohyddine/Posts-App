package com.mehyo.postsapp

import retrofit2.Response

class NetworkRepository(private val api: PostsAPI) {

    //Get Posts From API
    suspend fun getPosts(): Response<List<Post>> {
        return api.getPosts()
    }

    //Delete a post by id from API
    suspend fun deletePostById(id: Int): Response<Any?> {
        return api.deletePostById(id)
    }

    //Add a new post to API
    suspend fun addPost(body: Post): Response<Post> {
        return api.addPost(body)
    }

    //Edit a post by id from API
    suspend fun editPost(body: Post, id: Int): Response<Post> {
        return api.editPost(body, id)
    }

    //Get a Post by id From API
    suspend fun getPostById(id: Int): Response<Post> {
        return api.getPostById(id)
    }
}