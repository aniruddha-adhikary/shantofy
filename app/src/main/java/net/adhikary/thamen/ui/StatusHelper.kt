package net.adhikary.thamen.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.core.content.ContextCompat

data class AppStatus(
    val isNotificationPermissionGranted: Boolean,
    val isNotificationListenerEnabled: Boolean,
    val isServiceRunning: Boolean
)

class StatusHelper(private val context: Context) {
    fun getAppStatus(): AppStatus {
        return AppStatus(
            isNotificationPermissionGranted = checkNotificationPermission(),
            isNotificationListenerEnabled = isNotificationServiceEnabled(),
            isServiceRunning = true // TODO: Implement service status check
        )
    }

    private fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val flat: String = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":").toTypedArray()
            for (name in names) {
                val cn = ComponentName.unflattenFromString(name)
                if (cn != null) {
                    if (TextUtils.equals(context.packageName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
