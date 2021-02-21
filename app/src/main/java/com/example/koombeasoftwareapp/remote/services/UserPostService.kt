package com.example.koombeasoftwareapp.remote.services

import com.example.koombeasoftwareapp.models.response.UserPostResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface UserPostService {


    @GET("posts")
    fun getPosts(): Observable<UserPostResponse>

}