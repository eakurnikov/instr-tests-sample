package com.eakurnikov.instrsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eakurnikov.instrsample.common.Resource
import com.eakurnikov.instrsample.data.api.PostsApi
import com.eakurnikov.instrsample.data.dto.PostDto
import com.eakurnikov.instrsample.di.dependency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel(app: Application) : AndroidViewModel(app) {
    private val postsApi: PostsApi = app.dependency { networkComponent.postsApi }
    private var activeCall: Call<List<PostDto>>? = null

    private val _posts = MutableLiveData<Resource<List<PostDto>>>(Resource.Loading())
    val posts: LiveData<Resource<List<PostDto>>> = _posts

    private val onReceivePosts = object : Callback<List<PostDto>> {

        override fun onResponse(call: Call<List<PostDto>>, response: Response<List<PostDto>>) {
            if (response.isSuccessful) {
                val posts: List<PostDto>? = response.body()
                if (posts.isNullOrEmpty()) {
                    _posts.value = Resource.Success(emptyList())
                } else {
                    _posts.value = Resource.Success(posts)
                }
            } else {
                _posts.value = Resource.Error(response.code().toString())
            }
            activeCall = null
        }

        override fun onFailure(call: Call<List<PostDto>>, error: Throwable) {
            _posts.value = Resource.Error()
            activeCall = null
        }
    }

    override fun onCleared() {
        activeCall?.cancel()
        activeCall = null
    }

    fun onStart() {
        loadPosts()
    }

    fun onRefresh() {
        loadPosts()
    }

    private fun loadPosts() {
        _posts.value = Resource.Loading()
        activeCall?.cancel()
        activeCall = postsApi.getPosts().apply {
            enqueue(onReceivePosts)
        }
    }
}
