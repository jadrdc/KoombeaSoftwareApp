package com.example.koombeasoftwareapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.koombeasoftwareapp.models.entity.UserPost
import com.example.koombeasoftwareapp.models.response.UserPostResponse
import com.example.koombeasoftwareapp.repository.source.local.LocalPostRepository
import com.example.koombeasoftwareapp.repository.source.service.RemotePostRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class UserPostViewModel(application: Application) : AndroidViewModel(application) {

    var userPostList = MutableLiveData<MutableList<UserPost>>()
    private val disposables = CompositeDisposable()
    var isLoading = MutableLiveData<Boolean>(true)
    var errorMessage = MutableLiveData<String>()
    var localSourceRepository = LocalPostRepository()

    fun getUserPostLists() {


        var isCached = localSourceRepository.getIsCached()
        if (!isCached) {
            getRemoteUserPostList()
        } else {
            getCachedInfo()
        }
    }

    fun getCachedInfo() {
        var userList = localSourceRepository.getUserPostList()
        userPostList.postValue(userList)
        isLoading.postValue(false)
    }

    fun getRemoteUserPostList() {
        var remote = RemotePostRepository()

        disposables.add(
            remote.getPost().subscribeWith(object : DisposableObserver<UserPostResponse>() {
                override fun onComplete() {
                    isLoading.postValue(false)
                }

                override fun onNext(value: UserPostResponse?) {
                    userPostList.postValue(value?.data?.toMutableList())
                    localSourceRepository.saveUserPostList(value?.data!!.toMutableList())
                }

                override fun onError(e: Throwable?) {
                    isLoading.postValue(false)
                    errorMessage.postValue("Fallo viendo posts")
                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}