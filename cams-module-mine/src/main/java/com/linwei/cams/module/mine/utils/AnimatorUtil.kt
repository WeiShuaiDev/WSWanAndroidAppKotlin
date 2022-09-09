package com.linwei.cams.module.mine.utils

import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

object AnimatorUtil {

    private val FAST_OUT_SLOW_IN_INTERPOLATOR = LinearOutSlowInInterpolator()

    /**
     * 显示view
     */
    fun scaleShow(view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener?) {
        view.visibility = View.VISIBLE
        ViewCompat.animate(view)
            .scaleX(1.0f)
            .scaleY(1.0f)
            .alpha(1.0f)
            .setDuration(800)
            .setListener(viewPropertyAnimatorListener)
            .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
            .start()
    }

    /**
     * 隐藏view
     */
    fun scaleHide(view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener?) {
        ViewCompat.animate(view)
            .scaleX(0.0f)
            .scaleY(0.0f)
            .alpha(0.0f)
            .setDuration(800)
            .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
            .setListener(viewPropertyAnimatorListener)
            .start()
    }

    /**
     * 移动
     */
    fun tanslation(view: View, start: Float, end: Float) {
        val animator = ValueAnimator.ofFloat(start, end)
        view.visibility = View.VISIBLE
        animator.addUpdateListener {
            val value = animator.animatedValue as Float
            view.translationY = value
        }
        animator.duration = 200
        animator.interpolator = FAST_OUT_SLOW_IN_INTERPOLATOR
        animator.start()
    }

    /**
     * 高度
     */
    fun showHeight(view: View, start: Float, end: Float) {
        val animator = ValueAnimator.ofFloat(start, end)
        val layoutParams = view.layoutParams
        animator.addUpdateListener {
            val value = animator.animatedValue as Float
            layoutParams.height = value.toInt()
            view.layoutParams = layoutParams
        }
        animator.duration = 500
        animator.interpolator = AccelerateInterpolator()
        animator.start()
    }

    fun show(view: View, start: Int, end: Int) {
        val height = view.height
        val animator = ValueAnimator.ofInt(start, end)
        animator.addUpdateListener {
            val value = animator.animatedValue as Int
            view.top = value
            view.bottom = value + height
        }
        view.visibility = View.VISIBLE
        animator.duration = 200
        animator.interpolator = FAST_OUT_SLOW_IN_INTERPOLATOR
        animator.start()
    }
}