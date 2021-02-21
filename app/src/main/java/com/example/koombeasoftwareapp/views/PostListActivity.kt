package com.example.koombeasoftwareapp.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.bumptech.glide.Glide
import com.example.koombeasoftwareapp.R
import com.example.koombeasoftwareapp.adapters.UserPostListAdapter
import com.example.koombeasoftwareapp.custom.CustomSwipeBehavior
import com.example.koombeasoftwareapp.viewmodels.UserPostViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup_layout.view.*
import kotlinx.android.synthetic.main.userpost_list_item.view.firstImage


class PostListActivity : AppCompatActivity(), UserPostListAdapter.OnClickImageView {
    private val viewModel: UserPostViewModel? by viewModels()
    private var adapter = UserPostListAdapter(this, mutableListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUI()

        viewModel?.isLoading?.observe(this, Observer {
            setProgressBar(it)
        })

        viewModel?.errorMessage?.observe(this, Observer {
            displayErrorMessage(it)
        })

        viewModel?.userPostList?.observe(this, Observer {
            adapter.addUserPost(it)
        })


        viewModel?.getUserPostLists()
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            viewModel?.getRemoteUserPostList()
        }
    }

    override fun onClickImage(uri: String) {
        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_layout, null)


        Glide.with(this)
            .load(uri) // image url
            .into(view.firstImage) //
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setOnDismissListener {
            blurView.isVisible = false
        }
        var alert = alertDialogBuilder.show()
        blurView.isVisible = true


        val swipeImageTouchListener = CustomSwipeBehavior(view)

        view.rootPopup.setOnTouchListener(swipeImageTouchListener)
        swipeImageTouchListener.setSwipeListener(object : CustomSwipeBehavior.SwipeListener {
            override fun onDragStart() {
            }

            override fun onDragStop() {
            }

            override fun onDismissed() {
                alert.dismiss()
            }

        })


    }

    private fun setUI() {
        val linearLayoutManager: LinearLayoutManager? =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        userPostList?.layoutManager = linearLayoutManager
        userPostList?.adapter = adapter

    }

    private fun setProgressBar(isShowing: Boolean) {
        var visibility = View.GONE
        if (isShowing) {
            visibility = View.VISIBLE
        }
        progressBar.visibility = visibility

    }

    private fun displayErrorMessage(error: String) {
        var snackbar = Snackbar.make(rootView, error, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Intentar nuevamente") {
            viewModel?.getUserPostLists()
            snackbar.dismiss()
        }

        snackbar.show()
    }
}