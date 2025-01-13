package net.adhikary.thamen.dirty

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import net.adhikary.thamen.ShantofyNotificationListenerService

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // credits: https://stackoverflow.com/questions/11168869/starting-background-service-when-android-turns-on
        if (validActions.contains(intent.action)) {
            val serviceIntent = Intent(context, ShantofyNotificationListenerService::class.java)
            context.startService(serviceIntent)
        }
    }

    companion object {
        val validActions = setOf(
            "android.intent.action.BOOT_COMPLETED",
            "android.intent.action.LOCKED_BOOT_COMPLETED",
            "android.intent.action.QUICKBOOT_POWERON",
        )
    }
}