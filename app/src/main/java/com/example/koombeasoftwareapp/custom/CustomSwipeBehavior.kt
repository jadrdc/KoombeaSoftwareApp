package com.example.koombeasoftwareapp.custom

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CustomSwipeBehavior(private val swipeView: View) : View.OnTouchListener {

    interface SwipeListener {
        fun onDragStart()
        fun onDragStop()
        fun onDismissed()
    }

    companion object {
        private const val TAG = "SwipeImageTouchListener"
    }

    // Allows us to know if we should use MotionEvent.ACTION_MOVE
    private var tracking = false

    // The Position where our touch event started
    private var startY: Float = 0.0f
    private var swipeListener: SwipeListener? = null
    private var isDragStarted = false

    fun setSwipeListener(swipeListener: SwipeListener) {
        this.swipeListener = swipeListener
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    val hitRect = Rect()
                    swipeView.getHitRect(hitRect)
                    if (hitRect.contains(event.x.toInt(), event.y.toInt()))
                        tracking = true
                    startY = it.y
                    return true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    tracking = false
                    animateSwipeView(v!!.height)
                    return true
                }

                MotionEvent.ACTION_MOVE -> {
                    if (tracking) {
                        swipeView.translationY = it.y - startY
                        if (!isDragStarted) {
                            isDragStarted = true
                            swipeListener?.onDragStart()
                        }

                    }
                    return true
                }

                else -> {
                    false
                }
            }
        }

        return false
    }

    /**
     * Using the current translation of swipeView, decide if it has moved
     * to the point where we want to remove it.
     */
    private fun animateSwipeView(parentHeight: Int) {
            swipeListener?.onDismissed()

    }
}