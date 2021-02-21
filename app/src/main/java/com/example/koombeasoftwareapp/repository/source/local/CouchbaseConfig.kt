package com.example.koombeasoftwareapp.repository.source.local

import android.content.Context
import com.couchbase.lite.CouchbaseLite
import com.couchbase.lite.Database

import com.couchbase.lite.DatabaseConfiguration


object CouchbaseConfig {

     var database: Database? = null

    fun initCouchBase(context: Context?) {
        CouchbaseLite.init(context!!)
        val config = DatabaseConfiguration()
        database = Database("koombea", config)
}
}