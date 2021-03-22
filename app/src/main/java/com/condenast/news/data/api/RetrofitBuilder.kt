package com.condenast.news.data.api

import com.condenast.news.util.Constants.API_KEY
import com.condenast.news.util.Constants.NEWS_API_BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain)}  // add this in case api has to be added
        return Retrofit.Builder()
            .baseUrl(NEWS_API_BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * To add API_KEY query for every rest call
     */
    private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }

    val apiService: ApiService by lazy { getRetrofit().create(ApiService::class.java) }
}