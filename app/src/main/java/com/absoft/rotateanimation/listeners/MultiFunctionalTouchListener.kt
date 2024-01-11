package com.absoft.rotateanimation.listeners

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View

class MultiFunctionalTouchListener(val view: View, val eventListener: EventListener) :
    View.OnTouchListener {

    val handler = Looper.myLooper()?.let { Handler(it) }

    private var downTime = 0L
    private var clickCount = 0

    private val holdRunnable = object : Runnable {
        override fun run() {
            if (view.isEnabled) {
                eventListener.onHolding(view)
                handler?.postDelayed(this, 150)
            }
        }
    }

    private val holdStart = Runnable { eventListener.onHoldStart(view) }

    private val clickAction = Runnable {
        Log.e("TAG", "Runnable: count: $clickCount time: ${System.currentTimeMillis()}", )
        clickCount = 0
        eventListener.onClick(view)
    }

    init {
        view.setOnClickListener(null)
        view.setOnLongClickListener(null)
    }

    override fun onTouch(v: View?, e: MotionEvent?): Boolean {
        if (v?.id == view.id)
            when (e?.action) {
                MotionEvent.ACTION_DOWN -> {
                    downTime = System.currentTimeMillis()
                    handler?.postDelayed(holdStart, 200)
                    handler?.postDelayed(holdRunnable, 200)
                    handler?.removeCallbacks(clickAction)
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    return true
                }

                MotionEvent.ACTION_UP -> {
                    if ((System.currentTimeMillis() - downTime) < 200) { // Click Performed
                        when (clickCount) {
                            0 -> {
                                handler?.postDelayed(clickAction, 200)
                                clickCount += 1
                                v.performClick()
                            }

                            else -> {
                                eventListener.onDoubleClick(v)
                                clickCount = 0
                            }
                        }
                    } else {
                        clickCount = 0
                        eventListener.onHoldEnd(v)
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY_RELEASE)
                    }
                    handler?.removeCallbacks(holdStart)
                    handler?.removeCallbacks(holdRunnable)
                    return true
                }
            }
        return v?.id == view.id
    }

    interface EventListener {
        fun onClick(v: View?) {}
        fun onHoldStart(v: View?) {}
        fun onHolding(v: View?) {}
        fun onHoldEnd(v: View?) {}
        fun onDoubleClick(v: View?) {}
    }

}