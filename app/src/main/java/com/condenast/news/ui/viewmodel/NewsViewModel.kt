package com.condenast.news.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.condenast.news.data.repository.NewsRepository
import com.condenast.news.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel(){

    val topUSNews= MutableLiveData<Resource<*>>()

    fun getTopUSNews(countryCode: String, category: String) {
        topUSNews.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            val data = newsRepository.getTopUSNews(countryCode, category)
            if(data.isSuccessful) {
                topUSNews.postValue(
                    Resource.success(data = data)
                )
            } else {
                topUSNews.postValue(Resource.error(data,message = data.errorBody().toString() ?: "Error Occurred!"))
            }
        }
    }
}
