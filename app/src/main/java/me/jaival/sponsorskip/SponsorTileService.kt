package me.jaival.sponsorskip

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast

class SponsorTileService : TileService() {
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onStartListening() {
        super.onStartListening()
        SettingsManager.init(this)
        updateTile()
    }

    override fun onClick() {
        super.onClick()
        val nextState = !SettingsManager.isServiceEnabled
        SettingsManager.isServiceEnabled = nextState
        sendBroadcast(Intent(SettingsManager.ACTION_TOGGLE_SERVICE).setPackage(packageName))
        
        mainHandler.post {
            Toast.makeText(
                applicationContext,
                if (nextState) R.string.service_on else R.string.service_off,
                Toast.LENGTH_SHORT
            ).show()
        }
        
        updateTile()
    }

    private fun updateTile() {
        val tile = qsTile ?: return
        val isEnabled = SettingsManager.isServiceEnabled

        tile.state = if (isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.label = getString(R.string.app_name)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.subtitle = getString(if (isEnabled) R.string.on else R.string.off)
        }
        tile.updateTile()
    }
}
