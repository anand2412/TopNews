package com.condenast.news.data.repository

import com.condenast.news.data.api.ApiHelper

class DataRepository(private val apiHelper: ApiHelper) {
    suspend fun getTopUSNews(countryCode: String, category: String) =
        apiHelper.getTopUSNews(countryCode, category)

    suspend fun getLikes(url: String) =
        apiHelper.getLike(url)

    suspend fun getComments(url: String) =
        apiHelper.getComment(url)
}