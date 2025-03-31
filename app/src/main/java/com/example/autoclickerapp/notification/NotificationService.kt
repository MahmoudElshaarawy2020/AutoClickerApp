package com.example.autoclickerapp.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.autoclickerapp.R
import com.example.autoclickerapp.auto_scroll.AutoScrollService

class NotificationService : Service() {
    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    private fun startForegroundService() {
        val notification = createNotification()
        startForeground(1, notification)
    }

    private fun sendBroadcast(action: String) {
        val intent = Intent(action).apply {
            setPackage("com.example.autoclickerapp") // Ensure it's sent to the correct package
        }
        sendBroadcast(intent)
        Log.d("NotificationService", "Broadcast sent: $action")
    }

    private fun createNotification(): Notification {
        val channelId = "auto_scroll_service"
        val channelName = "Auto Scroll Service"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        // Intent for "Start Scrolling" button
        val startIntent = Intent(this, NotificationService::class.java).apply {
            action = "START_SCROLLING"
        }
        val startPendingIntent = PendingIntent.getService(this, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Intent for "Stop Scrolling" button
        val stopIntent = Intent(this, NotificationService::class.java).apply {
            action = "STOP_SCROLLING"
        }
        val stopPendingIntent = PendingIntent.getService(this, 1, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Auto Scroll Service")
            .setContentText("The auto-scrolling service is running.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setOngoing(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())  // Ensures notification is always expanded
            .addAction(R.drawable.ic_launcher_foreground, "▶ Start", startPendingIntent)  // Start button
            .addAction(R.drawable.ic_launcher_foreground, "⏹ Stop", stopPendingIntent)  // Stop button
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START_SCROLLING" -> {
                sendBroadcast(AutoScrollService.ACTION_START_SCROLL)  // Send broadcast
                Toast.makeText(this, "Scrolling Started", Toast.LENGTH_SHORT).show()
            }
            "STOP_SCROLLING" -> {
                sendBroadcast(AutoScrollService.ACTION_STOP_SCROLL)  // Send broadcast
                Toast.makeText(this, "Scrolling Stopped", Toast.LENGTH_SHORT).show()
            }
        }
        return START_STICKY
    }



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
