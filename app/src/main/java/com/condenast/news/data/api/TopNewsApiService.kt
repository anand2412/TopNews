package com.condenast.news.data.api

import com.condenast.news.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Response

interface TopNewsApiService {

    @GET("v2/top-headlines")
    @Headers("Content-Type: application/json")
    suspend fun getTopUSNews(
        @Query("country") countryCode: String = "us",
        @Query("category") pageNumber: String = "business"
    ) : Response<NewsResponse>

}