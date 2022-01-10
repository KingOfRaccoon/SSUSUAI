package com.castprogramms.ssusuai.tools.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.tools.chat.items.ChatTitleDateItem
import kotlin.math.abs
import kotlin.math.min

class MessageSwipeController(private val context: Context, private val swipeControllerActions: SwipeControllerActions) :
    ItemTouchHelper.Callback() {

    private lateinit var imageDrawable: Drawable
    private lateinit var shareRound: Drawable

    private var currentItemViewHolder: RecyclerView.ViewHolder? = null
    private lateinit var mView: View
    private var dX = 0f
    lateinit var metrics: DisplayMetrics

    private var replyButtonProgress: Float = 0f
    private var lastReplyButtonAnimationTime: Long = 0
    private var swipeBack = false
    private var isVibrate = false
    private var startTracking = false

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        mView = viewHolder.itemView
        metrics = mView.context.resources.displayMetrics
        imageDrawable = context.getDrawable(R.drawable.reply)!!
        shareRound = context.getDrawable(R.drawable.reply_round)!!
        return makeMovementFlags(
            ItemTouchHelper.ACTION_STATE_SWIPE,
            ItemTouchHelper.LEFT
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (viewHolder.itemViewType != R.layout.item_chat_date) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                setTouchListener(recyclerView, viewHolder)
            }
            if (-mView.translationX < convertTodp(metrics.widthPixels / 5) || dX > this.dX) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                this.dX = dX
                startTracking = true
            }
            currentItemViewHolder = viewHolder
            drawReplyButton(c)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        recyclerView.setOnTouchListener { _, event ->
            swipeBack = event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            if (swipeBack) {
                if (abs(mView.translationX) >= metrics.widthPixels/6) {
                    swipeControllerActions.showReplyUI(viewHolder.adapterPosition)
                }
            }
            false
        }
    }

    private fun drawReplyButton(canvas: Canvas) {
        if (currentItemViewHolder == null) {
            return
        }
        val translationX = -mView.translationX
        val newTime = System.currentTimeMillis()
        val dt = min(10, newTime - lastReplyButtonAnimationTime)
        lastReplyButtonAnimationTime = newTime
        val showing = translationX >= convertTodp(
            convertTodp(metrics.widthPixels/10)
        )

        if (showing) {
            if (replyButtonProgress < 1.0f) {
                replyButtonProgress += dt / 180.0f
                if (replyButtonProgress > 1.0f) {
                    replyButtonProgress = 1.0f
                } else {
                    mView.invalidate()
                }
            }
        } else if (translationX == 0.0f) {
            replyButtonProgress = 0f
            startTracking = false
            isVibrate = false
        } else {
            if (replyButtonProgress > 0.0f) {
                replyButtonProgress -= dt / 180.0f
                if (replyButtonProgress < 0.1f) {
                    replyButtonProgress = 0f
                } else {
                    mView.invalidate()
                }
            }
        }
        val alpha: Int
        val scale: Float
        if (showing) {
            scale = if (replyButtonProgress <= 0.8f) {
                1.2f * (replyButtonProgress / 0.8f)
            } else {
                1.2f - 0.2f * ((replyButtonProgress - 0.8f) / 0.2f)
            }
            alpha = min(255f, 255 * (replyButtonProgress / 0.8f)).toInt()
        } else {
            scale = replyButtonProgress
            alpha = min(255f, 255 * replyButtonProgress).toInt()
        }
        shareRound.alpha = alpha

        imageDrawable.alpha = alpha
        if (startTracking) {
            if (!isVibrate && mView.translationX <= convertTodp(-metrics.widthPixels/6)) {
                mView.performHapticFeedback(
                    HapticFeedbackConstants.KEYBOARD_TAP,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                )
                isVibrate = true
            }
        }

        val x: Int = if (mView.translationX > convertTodp(
                metrics.widthPixels/10
            )) {
            metrics.widthPixels/10 * 9
        } else {
            metrics.widthPixels + (mView.translationX / 2).toInt()
        }

        val y = (mView.top + mView.measuredHeight / 2).toFloat()
        shareRound.colorFilter =
            PorterDuffColorFilter(ContextCompat.getColor(context, R.color.color_reply), PorterDuff.Mode.MULTIPLY)

        shareRound.setBounds(
            (x - convertTodp(56) * scale).toInt(),
            (y - convertTodp(56) * scale).toInt(),
            (x + convertTodp(56) * scale).toInt(),
            (y + convertTodp(56) * scale).toInt()
        )
        shareRound.draw(canvas)
        imageDrawable.setBounds(
            (x - convertTodp(48) * scale).toInt(),
            (y - convertTodp(48) * scale).toInt(),
            (x + convertTodp(48) * scale).toInt(),
            (y + convertTodp(48) * scale).toInt()
        )
        imageDrawable.draw(canvas)
        shareRound.alpha = 255
        imageDrawable.alpha = 255
    }

    private fun convertTodp(pixel: Int): Int {
//        return AndroidUtils.dp(pixel.toFloat(), context)
        return pixel
    }
}