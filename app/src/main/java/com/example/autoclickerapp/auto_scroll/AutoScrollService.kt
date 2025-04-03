package com.example.autoclickerapp.auto_scroll

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Point
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.autoclickerapp.model.Identifier
import kotlinx.coroutines.*

class AutoScrollService : AccessibilityService() {

    private var isScrolling = false
    private val handler = CoroutineScope(Dispatchers.Main)

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }

//    override fun onServiceConnected() {
//        super.onServiceConnected()
//        Log.d("AutoScrollService", "Accessibility Service Connected")
//        startAutoScrolling()
//    }

//    private fun startAutoScrolling() {
//        isScrolling = true
//
//        handler.launch {
//            while (isScrolling) {
//                val swipe = DumbAction.DumbSwipe(
//                    id = Identifier(
//                        tempId = System.currentTimeMillis(),
//                    ),
//                    scenarioId = Identifier(
//                        tempId = System.currentTimeMillis()
//                    ),
//                    name = "Scroll Gesture",
//                    fromPosition = Point(500, 1500), // نقطة البداية (أسفل الشاشة)
//                    toPosition = Point(500, 500),   // نقطة النهاية (أعلى الشاشة)
//                    swipeDurationMs = 500, // مدة السحب
//                    repeatCount = Int.MAX_VALUE, // تكرار لا نهائي
//                    isRepeatInfinite = true,
//                    repeatDelayMs = 2000, // تأخير التمرير كل ثانيتين
//                    priority = 0
//                )
//
//                executeDumbSwipe(swipe)
//                delay(swipe.repeatDelayMs)
//            }
//        }
//    }

//    private suspend fun executeDumbSwipe(dumbSwipe: DumbAction.DumbSwipe) {
//        val path = Path().apply {
//            moveTo(dumbSwipe.fromPosition.x.toFloat(), dumbSwipe.fromPosition.y.toFloat())
//            lineTo(dumbSwipe.toPosition.x.toFloat(), dumbSwipe.toPosition.y.toFloat())
//        }
//
//        val swipeGesture = GestureDescription.Builder()
//            .addStroke(GestureDescription.StrokeDescription(path, 0, dumbSwipe.swipeDurationMs))
//            .build()
//
//        withContext(Dispatchers.Main) {
//            if (!isScrolling) return@withContext
//            dispatchGesture(swipeGesture, null, null) // ✅ تنفيذ التمرير داخل `AccessibilityService`
//            Log.d("AutoScrollService", "Swiping from ${dumbSwipe.fromPosition} to ${dumbSwipe.toPosition}")
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        isScrolling = false
        handler.cancel()
        Log.d("AutoScrollService", "Service Stopped")
    }
}
