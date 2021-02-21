package com.example.koombeasoftwareapp.repository.abstract

import com.example.koombeasoftwareapp.models.response.UserPostResponse
import io.reactivex.Observable

interface PostRepository {
    fun getPost(): Observable<UserPostResponse>
}