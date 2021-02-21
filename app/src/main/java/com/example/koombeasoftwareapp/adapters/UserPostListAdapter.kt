package com.example.koombeasoftwareapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koombeasoftwareapp.R
import com.example.koombeasoftwareapp.models.entity.UserPhotoGallery
import com.example.koombeasoftwareapp.models.entity.UserPost
import com.example.koombeasoftwareapp.utils.StringUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.userpost_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.days

class UserPostListAdapter(
    private val context: Context,
    var userPostList: MutableList<UserPost>,
    var clickListener: OnClickImageView
) :
    RecyclerView.Adapter<UserPostListAdapter.UserPostListViewholder>() {


    fun addUserPost(userPosts: MutableList<UserPost>) {
        userPostList = userPosts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostListViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.userpost_list_item, parent, false)
        return UserPostListViewholder(view)
    }

    override fun getItemCount(): Int {
        return userPostList.size
    }

    override fun onBindViewHolder(holder: UserPostListViewholder, position: Int) {
        holder.drawUserPost(userPostList[position], listener = clickListener)
    }


    public interface OnClickImageView {
        fun onClickImage(uri:String)
    }

    public class UserPostListViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun setPhotoGallery(photoGallery: MutableList<UserPhotoGallery>,listener: OnClickImageView) {
            var adapter = PhotoGalleryAdapter(itemView.context, photoGallery ,listener)

            val linearLayoutManager: LinearLayoutManager? =
                LinearLayoutManager(
                    itemView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                );
            itemView.rcPictureList?.layoutManager = linearLayoutManager
            itemView.rcPictureList?.adapter = adapter

        }

        fun drawUserPost(userPost: UserPost, listener: OnClickImageView) {
            itemView.txtUsername.text = userPost.name
            itemView.txtEmail.text = userPost.email
            Glide.with(itemView.context)
                .load(userPost.profile_pic) // image url
                .into(itemView.profilePhoto) // resizing}
            itemView.txtPostDate.text = StringUtils.formatDateOrdinal(userPost.post.date)



            if (userPost.post.pics.size == 2) {
                itemView.firstImage.visibility = View.GONE
                var userPhotoGalleryList = mutableListOf<UserPhotoGallery>()
                userPost.post.pics.forEach {
                    userPhotoGalleryList.add(UserPhotoGallery(it, userPost.post.pics.size))
                }
                setPhotoGallery(userPhotoGalleryList,listener)
            } else {
                itemView?.firstImage.setOnClickListener {
                    listener.onClickImage(userPost.post.pics[0])
                }
                Glide.with(itemView.context)
                    .load(userPost.post.pics[0])
                    .into(itemView.firstImage)
                var userPhotoGalleryList = mutableListOf<UserPhotoGallery>()
                userPost.post.pics.subList(1, userPost.post.pics.size).forEach {
                    userPhotoGalleryList.add(UserPhotoGallery(it, userPost.post.pics.size))
                }
                setPhotoGallery(userPhotoGalleryList,listener)

            }
        }
    }
}
