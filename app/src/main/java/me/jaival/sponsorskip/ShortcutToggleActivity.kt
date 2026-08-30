package me.jaival.sponsorskip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

class ShortcutToggleActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingsManager.init(this)

        val nextState = !SettingsManager.isServiceEnabled
        SettingsManager.isServiceEnabled = nextState
        sendBroadcast(Intent(SettingsManager.ACTION_TOGGLE_SERVICE).setPackage(packageName))

        Toast.makeText(this, if (nextState) "Sponsor Skip: ON" else "Sponsor Skip: OFF", Toast.LENGTH_SHORT).show()
        finish()
    }
}
