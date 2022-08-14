package com.mehyo.postsapp.network

import com.mehyo.postsapp.model.Post
import retrofit2.Response
import retrofit2.http.*

interface PostsAPI {

    /**
     * suspending function for GET "posts" network call.
     * @return all posts.
     */
    @GET("posts")
    suspend fun getPosts(
        @Query(value = "userId") userId: Int = 1
    ): Response<List<Post>>

    /**
     * suspending function for DELETE "posts/{id}" network call to
     * delete a post by id.
     * @return an emptyList.
     */
    @DELETE("posts/{id}")
    suspend fun deletePostById(
        @Path("id") id: Int
    ): Response<Any?>

    /**
     * suspending function for POST "posts" network call to
     * add a new post.
     * @return the newly created post.
     */
    @POST("posts")
    suspend fun addPost(
        @Body body: Post
    ): Response<Post>

    /**
     * suspending function for PUT "posts/{id}" network call to
     * edit a post by id.
     * @return the edited post.
     */
    @PUT("posts/{id}")
    suspend fun editPostById(
        @Body body: Post,
        @Path("id") id: Int
    ): Response<Post>

    /**
     * suspending function for GET "posts/{id}" network call to
     * get a post by id.
     * @return the requested post details.
     */
    @GET("posts/{id}")
    suspend fun getPostById(
        @Path("id") id: Int
    ): Response<Post>

}