package net.adhikary.thamen.ui

import android.Manifest
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.os.PowerManager
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import net.adhikary.thamen.ShantofyNotificationListenerService

data class AppStatus(
    val isNotificationPermissionGranted: Boolean,
    val isNotificationListenerEnabled: Boolean,
    val isServiceRunning: Boolean,
    val batteryOptimizationState: BatteryOptimizationState
)

class StatusHelper(private val context: Context) {
    private fun isServiceRunning(): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        
        // Check if notification listener is enabled
        val enabledListeners = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        )
        val isEnabled = enabledListeners?.contains(context.packageName) == true
        
        // Check if our process is running
        val runningProcesses = manager.runningAppProcesses
        val isProcessRunning = runningProcesses?.any { 
            it.processName == context.packageName 
        } ?: false
        
        return isEnabled && isProcessRunning
    }

    fun getAppStatus(): AppStatus {
        return AppStatus(
            isNotificationPermissionGranted = checkNotificationPermission(),
            isNotificationListenerEnabled = isNotificationServiceEnabled(),
            isServiceRunning = isServiceRunning(),
            batteryOptimizationState = getBatteryOptimizationState()
        )
    }

    private fun getBatteryOptimizationState(): BatteryOptimizationState {
        val powerManager = context.getSystemService<PowerManager>()
        return when {
            powerManager == null -> BatteryOptimizationState.UNKNOWN
            powerManager.isIgnoringBatteryOptimizations(context.packageName) -> BatteryOptimizationState.DISABLED
            else -> BatteryOptimizationState.ENABLED
        }
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
