package com.example.autoclickerapp.auto_scroll

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AutoScrollReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AutoScrollReceiver", "📩 Received Broadcast: ${intent?.action}")

        val serviceIntent = Intent(context, AutoScrollService::class.java).apply {
            action = intent?.action
        }
        context?.startService(serviceIntent)
    }
}
