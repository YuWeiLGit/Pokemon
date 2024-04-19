package com.example.pokemonbook.utils

import android.animation.ValueAnimator
import android.view.View

object ViewAnimatorUtil {
    private fun isViewGone(view: View): Boolean {
        return view.visibility == View.GONE
    }

    fun expand(view: View) {
        if (isViewGone(view)) {
            view.visibility = View.VISIBLE
            view.measure(
                View.MeasureSpec.makeMeasureSpec(
                    (view.parent as View).width,
                    View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            val targetHeight = view.measuredHeight

            view.layoutParams.height = 1
            view.visibility = View.VISIBLE

            val animator = ValueAnimator.ofInt(1, targetHeight)
            animator.addUpdateListener { animation ->
                view.layoutParams.height = animation.animatedValue as Int
                view.requestLayout()
            }
            animator.duration =
                ((targetHeight / view.context.resources.displayMetrics.density) / 8).toLong()
            animator.start()
        }
    }


    fun collapse(view: View) {
        val initialHeight = view.measuredHeight

        val animator = ValueAnimator.ofInt(initialHeight, 1)
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            if (value == 1) {
                view.visibility = View.GONE
            } else {
                view.layoutParams.height = value
                view.requestLayout()
            }

            if (animation.animatedFraction == 1f) {
                view.visibility = View.GONE
            }
        }
        animator.duration =
            ((initialHeight / view.context.resources.displayMetrics.density) / 10).toLong()
        animator.start()
    }
}