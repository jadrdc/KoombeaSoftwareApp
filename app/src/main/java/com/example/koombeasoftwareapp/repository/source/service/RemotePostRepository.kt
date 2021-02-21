package com.example.koombeasoftwareapp.repository.source.service

import com.example.koombeasoftwareapp.models.entity.UserPost
import com.example.koombeasoftwareapp.models.response.UserPostResponse
import com.example.koombeasoftwareapp.remote.config.RetrofitInstance
import com.example.koombeasoftwareapp.remote.services.UserPostService
import com.example.koombeasoftwareapp.repository.abstract.PostRepository
import io.reactivex.Observable

class RemotePostRepository : PostRepository {

    override fun getPost(): Observable<UserPostResponse> {
        val request = RetrofitInstance.buildService(UserPostService::class.java)
        return request.getPosts()
    }
}