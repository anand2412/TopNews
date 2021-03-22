package com.condenast.news.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getTopUSNews(countryCode: String, category: String) =
        apiService.getTopUSNews(countryCode, category)
}