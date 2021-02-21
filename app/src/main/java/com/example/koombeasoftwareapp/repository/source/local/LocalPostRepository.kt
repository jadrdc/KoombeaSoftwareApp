package com.example.koombeasoftwareapp.repository.source.local

import com.couchbase.lite.*
import com.couchbase.lite.Array
import com.couchbase.lite.DataSource.database
import com.example.koombeasoftwareapp.models.entity.Post
import com.example.koombeasoftwareapp.models.entity.UserPost


class LocalPostRepository {


    fun saveUserPostList(list: MutableList<UserPost>) {

        list.forEach {
            var usersListPost = MutableDocument()
            usersListPost.setString("name", it.name)
            usersListPost.setString("uid", it.uid)
            usersListPost.setString("email", it.email)
            usersListPost.setString("profile_pic", it.profile_pic)
            usersListPost.setString("entity", "post")
            var dictionary = MutableDictionary()
            dictionary.setString("date", it.post.date)
            dictionary.setInt("id", it.post.id)
            dictionary.setValue("pics", it.post.pics)
            usersListPost.setDictionary("post", dictionary)
            CouchbaseConfig.database?.save(usersListPost)
        }
        saveIsCached()

    }


    fun saveIsCached() {
        val mapIsCached = HashMap<String, Any>()
        mapIsCached["isCached"] = true
        val isChached = MutableDocument("ISCACHED", mapIsCached)
        CouchbaseConfig.database?.save(isChached)
    }

    fun getIsCached(): Boolean {
        var info = CouchbaseConfig.database?.getDocument("ISCACHED");
        var isCached = false
        info?.getBoolean("isCached")?.let { isCached = it }
        return isCached

    }

    fun getUserPostList(): MutableList<UserPost> {
        var userlist = mutableListOf<UserPost>()

        CouchbaseConfig.database?.let {
            val query: Query = QueryBuilder.select(
                SelectResult.property("name"),
                SelectResult.property("email"),
                SelectResult.property("uid"),
                SelectResult.property("profile_pic"),
                SelectResult.property("post"),
                SelectResult.property("date"),
                SelectResult.property("id"),
                SelectResult.property("pics")
            )
                .from(database(it))
                .where(
                    Expression.property("entity").equalTo(Expression.string("post"))
                )

            for (result in query.execute()) {
                val name = result.getString("name");
                val email = result.getString("email");
                val uid = result.getString("uid");
                val profile_pic = result.getString("profile_pic");
                var dictionary = result.getDictionary("post")
                var date = dictionary?.getString("date")
                var id = dictionary?.getInt("id")
                var pics = mutableListOf<String>()
                ((dictionary?.getValue("pics")) as (Array)
                        ).forEach { pic ->
                        pics.add(pic.toString())
                    }


                var post = Post(id = id!!, date = date!!, pics = pics)
                var userPost = UserPost(
                    name = name!!,
                    email = email!!,
                    profile_pic = profile_pic!!,
                    post = post,
                    uid = uid!!
                )
                userlist.add(userPost)
            }
        }
        return userlist

    }


}