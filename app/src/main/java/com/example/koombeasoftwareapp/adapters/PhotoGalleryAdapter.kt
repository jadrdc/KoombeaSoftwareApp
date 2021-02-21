package com.example.koombeasoftwareapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koombeasoftwareapp.R
import com.example.koombeasoftwareapp.models.entity.UserPhotoGallery
import kotlinx.android.synthetic.main.gallerypost_list_item.view.*

class PhotoGalleryAdapter(
    private val context: Context,
    var userPhotoGalleryList: MutableList<UserPhotoGallery>,
    var listener: UserPostListAdapter.OnClickImageView
) :
    RecyclerView.Adapter<PhotoGalleryAdapter.PhotoGalleyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGalleyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallerypost_list_item, parent, false)
        return PhotoGalleyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userPhotoGalleryList.size
    }

    override fun onBindViewHolder(holder: PhotoGalleyViewHolder, position: Int) {
        holder.drawPhoto(userPhotoGalleryList[position], listener)
    }


    public class PhotoGalleyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun drawPhoto(
            photo: UserPhotoGallery,
            listener: UserPostListAdapter.OnClickImageView
        ) {
            var width = 300
            var height = 300
            if (photo.size == 2) {
                width = 600
                height = 600
            }
            itemView.photo.layoutParams.height = height
            itemView.photo.layoutParams.width = width
            itemView.photo.requestLayout()

            Glide.with(itemView.context).load(photo.uri)
                .into(itemView.photo)

            itemView.photo.setOnClickListener {
                listener.onClickImage(photo.uri)
            }
        }
    }


}