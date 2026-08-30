/*
 * Sponsor Skip - Auto-skips SponsorBlock segments in YouTube videos
 * Copyright (C) 2026 Jaival
 */

package io.github.ezn24.sponsorskip.bilibili

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat

class SkipperForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            val channelId = "foreground_keepalive"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, getString(R.string.foreground_channel), NotificationManager.IMPORTANCE_LOW)
                channel.description = getString(R.string.foreground_channel_description)
                getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
            }

            // Create an intent to open this specific notification channel in Android Settings
            val settingsIntent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
            }
            
            val pendingIntent = PendingIntent.getActivity(
                this, 0, settingsIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.foreground_notification_text))
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent) // Bind the click action
                .build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
                } else {
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
                }
                startForeground(1002, notification, type)
            } else {
                startForeground(1002, notification)
            }
        } catch (e: Exception) {
            AppLogger.log("[SERVICE] SkipperForegroundService startForeground failed: ${e.message}")
        }
        return START_STICKY
    }
}
