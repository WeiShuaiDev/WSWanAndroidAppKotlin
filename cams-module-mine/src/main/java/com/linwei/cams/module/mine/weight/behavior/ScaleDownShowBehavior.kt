package com.linwei.cams.module.mine.weight.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.linwei.cams.module.mine.utils.AnimatorUtil

/**
 * 下拉时显示FAB，上拉隐藏，留出更多位置给用户
 */
class ScaleDownShowBehavior() : FloatingActionButton.Behavior() {

    constructor(context: Context?, attrs: AttributeSet?) : this()

    /**
     * 退出动画是否正在执行。
     */
    private var mIsAnimatingOut = false

    private var mOnStateChangedListener: OnStateChangedListener? = null

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !mIsAnimatingOut && child.visibility == View.VISIBLE) { //往下滑
            child.visibility = View.INVISIBLE
            AnimatorUtil.scaleHide(child, mViewPropertyAnimatorListener)
            mOnStateChangedListener?.onChanged(false)
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && child.visibility != View.VISIBLE) {
            AnimatorUtil.scaleShow(child, null)
            mOnStateChangedListener?.onChanged(true)
        }
    }

    fun setOnStateChangedListener(mOnStateChangedListener: OnStateChangedListener?) {
        this.mOnStateChangedListener = mOnStateChangedListener
    }

    // 外部监听显示和隐藏。
    interface OnStateChangedListener {
        fun onChanged(isShow: Boolean)
    }

    fun <V : View?> from(view: V): ScaleDownShowBehavior {
        val params = view!!.layoutParams
        require(params is CoordinatorLayout.LayoutParams) { "The view is not a child of CoordinatorLayout" }
        val behavior = params.behavior
        require(behavior is ScaleDownShowBehavior) { "The view is not associated with ScaleDownShowBehavior" }
        return behavior
    }

    private val mViewPropertyAnimatorListener: ViewPropertyAnimatorListener =
        object : ViewPropertyAnimatorListener {
            override fun onAnimationStart(view: View) {
                mIsAnimatingOut = true
            }

            override fun onAnimationEnd(view: View) {
                mIsAnimatingOut = false
                // 注意不要设置为 Gone，这样在高版本的会导致 viewBehavior.onNestedScroll 没机会调用
                view.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(arg0: View) {
                mIsAnimatingOut = false
            }
        }
}