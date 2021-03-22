package com.condenast.news.data.repository

import com.condenast.news.data.api.ApiHelper

class NewsRepository(private val apiHelper: ApiHelper) {
    suspend fun getTopUSNews(countryCode: String, category: String) =
        apiHelper.getTopUSNews(countryCode, category)
}