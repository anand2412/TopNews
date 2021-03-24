package com.condenast.news.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.condenast.news.data.repository.DataRepository
import com.condenast.news.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikeCommentViewModel(private val dataRepository: DataRepository) : ViewModel(){

    val likes= MutableLiveData<Resource<*>>()
    val comments= MutableLiveData<Resource<*>>()

    fun getLikes(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = dataRepository.getLikes(url)
            if(data.isSuccessful) {
                likes.postValue(
                    Resource.success(data = data)
                )
            } else {
                likes.postValue(Resource.error(data,message = data.errorBody().toString() ?: "Error Occurred!"))
            }
        }
    }

    fun getComments(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = dataRepository.getComments(url)
            if(data.isSuccessful) {
                comments.postValue(
                    Resource.success(data = data)
                )
            } else {
                comments.postValue(Resource.error(data,message = data.errorBody().toString() ?: "Error Occurred!"))
            }
        }
    }
}