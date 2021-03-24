package com.condenast.news.data.api

object ApiHelper {

    private val retrofitBuilder = RetrofitBuilder()

    suspend fun getTopUSNews(countryCode: String, category: String) =
        retrofitBuilder.topNewsApiService.getTopUSNews(countryCode, category)

    suspend fun getLike(url:String) =
         retrofitBuilder.likeCommentApiService.getLikes(url)

    suspend fun getComment(url:String) =
        retrofitBuilder.likeCommentApiService.getComments(url)
}