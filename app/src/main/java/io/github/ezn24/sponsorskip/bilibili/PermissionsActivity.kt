/*
 * Sponsor Skip - Auto-skips SponsorBlock segments in YouTube videos
 * Copyright © 2026 Jaival
 */

package io.github.ezn24.sponsorskip.bilibili

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat

class PermissionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)
        findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        val permNotif = findViewById<TextView>(R.id.permNotif)
        val permToast = findViewById<TextView>(R.id.permToast)
        val permBattery = findViewById<TextView>(R.id.permBattery)
        val permHelp = findViewById<TextView>(R.id.permHelp)

        val hasListener = NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName)
        permNotif.text = getString(R.string.notification_listener_status, if (hasListener) "✅" else "❌")
        permNotif.setOnClickListener {
            if (hasListener) {
                Toast.makeText(this, R.string.notification_listener_granted, Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }
        }

        var hasToasts = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            hasToasts = checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        }
        permToast.text = getString(R.string.post_notifications_status, if (hasToasts) "✅" else "❌")
        permToast.setOnClickListener {
            if (hasToasts) {
                Toast.makeText(this, R.string.post_notifications_granted, Toast.LENGTH_SHORT).show()
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        val isIgnoringBattery = pm.isIgnoringBatteryOptimizations(packageName)
        permBattery.text = getString(R.string.battery_status, getString(if (isIgnoringBattery) R.string.unrestricted else R.string.optimized))

        permBattery.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.battery_optimization)
                .setMessage(R.string.battery_optimization_message)
                .setPositiveButton(R.string.disable) { _, _ ->
                    if (isIgnoringBattery) {
                        Toast.makeText(this, R.string.battery_already_disabled, Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                        intent.data = Uri.parse("package:$packageName")
                        startActivity(intent)
                    }
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }

        permHelp.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.read_carefully)
                .setMessage(R.string.restricted_permission_help)
                .setPositiveButton(R.string.app_info) { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                }
                .show()
        }
    }
}
