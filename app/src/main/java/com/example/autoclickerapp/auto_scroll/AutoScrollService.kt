package com.example.autoclickerapp.auto_scroll

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AutoScrollService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private var isScrolling = false
    private var scrollRunnable: Runnable? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // No specific event handling needed for scrolling
    }

    override fun onInterrupt() {
        Log.d("AutoScrollService", "Service Interrupted")
        stopAutoScroll()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("AutoScrollService", "✅ Service Connected!")

        val filter = IntentFilter().apply {
            addAction(ACTION_START_SCROLL)
            addAction(ACTION_STOP_SCROLL)
        }
        registerReceiver(scrollReceiver, filter)
        Log.d("AutoScrollService", "Broadcast receiver registered")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(scrollReceiver)
        Log.d("AutoScrollService", "Broadcast receiver unregistered")
    }

    private val scrollReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("AutoScrollService", "Received broadcast: ${intent?.action}")

            when (intent?.action) {
                ACTION_START_SCROLL -> {
                    Log.d("AutoScrollService", "Received START_SCROLLING broadcast")
                    startAutoScroll()
                }
                ACTION_STOP_SCROLL -> {
                    Log.d("AutoScrollService", "Received STOP_SCROLLING broadcast")
                    stopAutoScroll()
                }
            }
        }
    }

    fun startAutoScroll() {
        if (!isScrolling) {  // ✅ Fixes the 'not' issue
            isScrolling = true
            Log.d("AutoScrollService", "✅ Accessibility Service is Running. Starting Auto Scroll!")

            scrollRunnable = object : Runnable {
                override fun run() {
                    if (isScrolling) {
                        Log.d("AutoScrollService", "🔄 Calling performScrollAction() function...")
                        performScrollAction()
                        handler.postDelayed(this, SCROLL_INTERVAL_MS)
                    }
                }
            }
            handler.post(scrollRunnable!!)
        }
    }


    private fun stopAutoScroll() {
        if (!isScrolling) return

        isScrolling = false
        scrollRunnable?.let { handler.removeCallbacks(it) }
        scrollRunnable = null
        Log.d("AutoScrollService", "Auto Scroll Stopped")
    }

    private fun performScrollAction() {
        Log.d("AutoScrollService", "🚀 Trying GLOBAL_ACTION_SCROLL_FORWARD...")
//        performGlobalAction(AccessibilityService.GLOBAL_ACTION_SCROLL_FORWARD)
    }
    private fun performSwipe() {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val centerX = screenWidth / 2
        val startY = (screenHeight * 0.7).toFloat()
        val endY = (screenHeight * 0.3).toFloat()

        Log.d("AutoScrollService", "📌 Swiping from ($centerX, $startY) to ($centerX, $endY)")

        val path = Path().apply {
            moveTo(centerX.toFloat(), startY)
            lineTo(centerX.toFloat(), endY)
        }

        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, SWIPE_DURATION_MS))
            .build()

        dispatchGesture(gesture, object : AccessibilityService.GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                super.onCompleted(gestureDescription)
                Log.d("AutoScrollService", "✅ Swipe gesture executed successfully!")
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
                super.onCancelled(gestureDescription)
                Log.e("AutoScrollService", "❌ Swipe gesture was cancelled!")
            }
        }, null)
    }

    companion object {
        const val ACTION_START_SCROLL = "com.example.autoscroll.START_SCROLL"
        const val ACTION_STOP_SCROLL = "com.example.autoscroll.STOP_SCROLL"
        private const val SCROLL_INTERVAL_MS = 3000L
        private const val SWIPE_DURATION_MS = 500L
    }
}
