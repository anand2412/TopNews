package com.condenast.news.data.api

import com.condenast.news.data.model.CommentResponse
import com.condenast.news.data.model.LikeResponse
import com.condenast.news.data.model.NewsResponse
import retrofit2.Response

object ApiHelper :IApiHelper{

    private val retrofitBuilder = RetrofitBuilder()

    override suspend fun getTopUSNews(countryCode: String, category: String) =
        retrofitBuilder.topNewsApiService.getTopUSNews(countryCode, category)

    override suspend fun getLike(url:String) =
         retrofitBuilder.likeCommentApiService.getLikes(url)

    override suspend fun getComment(url:String) =
        retrofitBuilder.likeCommentApiService.getComments(url)
}

interface IApiHelper {
    suspend fun getTopUSNews(countryCode: String, category: String) : Response<NewsResponse>

    suspend fun getLike(url:String) : Response<LikeResponse>

    suspend fun getComment(url:String) : Response<CommentResponse>
}