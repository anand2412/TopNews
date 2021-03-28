package com.condenast.news.ui.viewmodel

import android.os.Looper
import com.condenast.news.BaseTest
import com.condenast.news.data.api.IApiHelper
import com.condenast.news.data.model.CommentResponse
import com.condenast.news.data.model.LikeResponse
import com.condenast.news.data.repository.DataRepository
import com.condenast.news.util.Status
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.robolectric.Shadows
import retrofit2.Response

class LikeCommentViewModelTest : BaseTest() {

    @Test
    fun test1() = runBlockingTest {
        val likeResponse = LikeResponse(1)
        val mockResponseSuccess = Response.success(likeResponse)
        val mockIApiHelper = mock<IApiHelper>()
        Mockito.`when`(mockIApiHelper.getLike(any())).thenReturn(mockResponseSuccess)
        val dataRepository = DataRepository(mockIApiHelper)
        val likeCommentViewModel = LikeCommentViewModel(dataRepository)
        likeCommentViewModel.likes.observeForever { }
        likeCommentViewModel.getLikes("test")
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        Assert.assertEquals(Status.SUCCESS, likeCommentViewModel.likes.value?.status)
    }

    @Test
    fun test2() = runBlockingTest {
        val mockResponseSuccess = Response.error<LikeResponse>(403 , TestResponseBody())
        val mockIApiHelper = mock<IApiHelper>()
        Mockito.`when`(mockIApiHelper.getLike(any())).thenReturn(mockResponseSuccess)
        val dataRepository = DataRepository(mockIApiHelper)
        val likeCommentViewModel = LikeCommentViewModel(dataRepository)
        likeCommentViewModel.likes.observeForever { }
        likeCommentViewModel.getLikes("test")
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        Assert.assertEquals(Status.ERROR, likeCommentViewModel.likes.value?.status)
    }

    @Test
    fun test3() = runBlockingTest {
        val commentResponse = CommentResponse(1)
        val mockResponseSuccess = Response.success(commentResponse)
        val mockIApiHelper = mock<IApiHelper>()
        Mockito.`when`(mockIApiHelper.getComment(any())).thenReturn(mockResponseSuccess)
        val dataRepository = DataRepository(mockIApiHelper)
        val likeCommentViewModel = LikeCommentViewModel(dataRepository)
        likeCommentViewModel.comments.observeForever { }
        likeCommentViewModel.getComments("test")
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        Assert.assertEquals(Status.SUCCESS, likeCommentViewModel.comments.value?.status)
    }

    @Test
    fun test4() = runBlockingTest {
        val mockResponseSuccess = Response.error<CommentResponse>(403 , TestResponseBody())
        val mockIApiHelper = mock<IApiHelper>()
        Mockito.`when`(mockIApiHelper.getComment(any())).thenReturn(mockResponseSuccess)
        val dataRepository = DataRepository(mockIApiHelper)
        val likeCommentViewModel = LikeCommentViewModel(dataRepository)
        likeCommentViewModel.comments.observeForever { }
        likeCommentViewModel.getLikes("test")
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        Assert.assertEquals(Status.ERROR, likeCommentViewModel.comments.value?.status)
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