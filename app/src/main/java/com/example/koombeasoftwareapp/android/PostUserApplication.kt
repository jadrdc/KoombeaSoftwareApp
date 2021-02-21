package com.example.koombeasoftwareapp.android

import android.app.Application
import com.example.koombeasoftwareapp.repository.source.local.CouchbaseConfig

class PostUserApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CouchbaseConfig.initCouchBase(this)
    }
}