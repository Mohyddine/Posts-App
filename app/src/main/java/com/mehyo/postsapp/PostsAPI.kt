package com.mehyo.postsapp

import retrofit2.Response
import retrofit2.http.*

interface PostsAPI {

    //suspending function for GET network call to get all posts
    @GET("posts")
    suspend fun getPosts(
        @Query(value = "userId") userId: Int = 1
    ): Response<List<Post>>

    //suspending function for DELETE network call to delete a post by id
    @DELETE("posts/{id}")
    suspend fun deletePostById(
        @Path("id") id: Int
    ): Response<Any?>

    //suspending function for POST network call to add a new post
    @POST("posts")
    suspend fun addPost(
        @Body body: Post
    ): Response<Post>

    //suspending function for PUT network call to edit a post by id
    @PUT("posts/{id}")
    suspend fun editPostById(
        @Body body: Post,
        @Path("id") id: Int
    ): Response<Post>

    //suspending function for GET network call to get a post by id
    @GET("posts/{id}")
    suspend fun getPostById(
        @Path("id") id: Int
    ): Response<Post>

}