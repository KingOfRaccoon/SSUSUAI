package com.castprogramms.ssusuai.ui.custombottomnavigationview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewPropertyAnimator
import androidx.annotation.Dimension
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.material.animation.AnimationUtils

open class HideBehaviorWithBlockChat<V : View> : CoordinatorLayout.Behavior<V> {
    private var height = 0
    private var currentState: Int = STATE_SCROLLED_UP
    private var additionalHiddenOffsetY = 0
    private var currentAnimator: ViewPropertyAnimator? = null
    private var isChat = false

    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onLayoutChild(
        parent: CoordinatorLayout, child: V, layoutDirection: Int
    ): Boolean {
        val paramsCompat = child.layoutParams as MarginLayoutParams
        height = child.measuredHeight + paramsCompat.bottomMargin
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    /**
     * Sets an additional offset for the y position used to hide the view.
     *
     * @param child the child view that is hidden by this behavior
     * @param offset the additional offset in pixels that should be added when the view slides away
     */
    fun setAdditionalHiddenOffsetY(child: V, @Dimension offset: Int) {
        additionalHiddenOffsetY = offset
        if (currentState == STATE_SCROLLED_DOWN) {
            child.translationY = (height + additionalHiddenOffsetY).toFloat()
        }
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyConsumed > 0) {
            slideDown(child)
        } else if (dyConsumed < 0) {
            slideUp(child)
        }
    }

    /**
     * Perform an animation that will slide the child from it's current position to be totally on the
     * screen.
     */
    fun slideUp(child: V) {
        if (!isChat) {
            if (currentState == STATE_SCROLLED_UP) {
                return
            }
            if (currentAnimator != null) {
                currentAnimator!!.cancel()
                child.clearAnimation()
            }
            currentState = STATE_SCROLLED_UP
            animateChildTo(
                child,
                0,
                ENTER_ANIMATION_DURATION.toLong(),
                AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR
            )
        }
    }

    /**
     * Perform an animation that will slide the child from it's current position to be totally off the
     * screen.
     */
    fun slideDown(child: V) {
        if (currentState == STATE_SCROLLED_DOWN) {
            return
        }
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
            child.clearAnimation()
        }
        currentState = STATE_SCROLLED_DOWN
        animateChildTo(
            child,
            height + additionalHiddenOffsetY,
            EXIT_ANIMATION_DURATION.toLong(),
            AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR
        )
    }

    private fun animateChildTo(
        child: V, targetY: Int, duration: Long, interpolator: TimeInterpolator
    ) {
        currentAnimator = child.animate()
            .translationY(targetY.toFloat())
            .setInterpolator(interpolator)
            .setDuration(duration)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        currentAnimator = null
                    }
                })
    }

    fun setIsChat(isChat: Boolean){
        this.isChat = isChat
    }

    companion object {
        protected const val ENTER_ANIMATION_DURATION = 225
        protected const val EXIT_ANIMATION_DURATION = 175
        private const val STATE_SCROLLED_DOWN = 1
        private const val STATE_SCROLLED_UP = 2
    }
}