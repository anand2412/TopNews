package com.condenast.news.data.api

import com.condenast.news.data.model.CommentResponse
import com.condenast.news.data.model.LikeResponse
import com.condenast.news.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface LikeCommentApiService {

    @GET("likes/{url}")
    @Headers("Content-Type: application/json")
    suspend fun getLikes(
        @Path("url") url:String) : Response<LikeResponse>

    @GET("comments/{url}")
    @Headers("Content-Type: application/json")
    suspend fun getComments(
        @Path("url") url:String) : Response<CommentResponse>
}