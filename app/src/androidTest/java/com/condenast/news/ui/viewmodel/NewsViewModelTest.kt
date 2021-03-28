package com.condenast.news

import android.os.Looper.getMainLooper
import com.condenast.news.data.api.IApiHelper
import com.condenast.news.data.model.Article
import com.condenast.news.data.model.NewsResponse
import com.condenast.news.data.repository.DataRepository
import com.condenast.news.ui.viewmodel.NewsViewModel
import com.condenast.news.util.Status
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.robolectric.Shadows.shadowOf
import retrofit2.Response

class NewsViewModelTest : BaseTest() {

    @Test
    fun test1() = runBlockingTest {
        val newsResponse = NewsResponse(mutableListOf<Article>(), "success", 1)
        val mockResponseSuccess = Response.success(newsResponse)
        val mockIApiHelper = mock<IApiHelper>()
        `when`(mockIApiHelper.getTopUSNews(any(), any())).thenReturn(mockResponseSuccess)
        val dataRepository = DataRepository(mockIApiHelper)
        val newsViewModel = NewsViewModel(dataRepository)
        newsViewModel.topUSNews.observeForever { }
        newsViewModel.getTopUSNews("test", "test1")
        shadowOf(getMainLooper()).idle()
        Assert.assertEquals(Status.SUCCESS, newsViewModel.topUSNews.value?.status)
    }

    @Test
    fun test2() = runBlockingTest {
        val mockResponseSuccess = Response.error<NewsResponse>(403 , TestResponseBody())
        val mockIApiHelper = mock<IApiHelper>()
        `when`(mockIApiHelper.getTopUSNews(any(), any())).thenReturn(mockResponseSuccess)
        val dataRepository = DataRepository(mockIApiHelper)
        val newsViewModel = NewsViewModel(dataRepository)
        newsViewModel.topUSNews.observeForever { }
        newsViewModel.getTopUSNews("test", "test1")
        shadowOf(getMainLooper()).idle()
        Assert.assertEquals(Status.ERROR, newsViewModel.topUSNews.value?.status)
    }

    inner class TestResponseBody : ResponseBody() {
        override fun contentLength(): Long {
            return 0L
        }

        override fun contentType(): MediaType? {
            return null
        }

        override fun source(): BufferedSource {
            return mock<BufferedSource>()
        }
    }
}