package com.condenast.news.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.condenast.news.data.api.ApiHelper
import com.condenast.news.data.repository.DataRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(DataRepository(apiHelper)) as T
        }
        else if (modelClass.isAssignableFrom(LikeCommentViewModel::class.java)) {
            return LikeCommentViewModel(DataRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}