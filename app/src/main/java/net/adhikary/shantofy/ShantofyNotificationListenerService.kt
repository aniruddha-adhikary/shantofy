package net.adhikary.shantofy

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.TextUtils
import android.util.Log
import net.adhikary.shantofy.core.RuleBasedNotificationChecker
import net.adhikary.shantofy.core.ShantofyNotification

class ShantofyNotificationListenerService : NotificationListenerService() {

    private val ruleBasedNotificationChecker by lazy { RuleBasedNotificationChecker(this) }
    private val enabledNotificationListeners = "enabled_notification_listeners"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerNotificationChannel()
        val notification = createNotification()
        startForeground(NOTIFICATION_CHANNEL_ID, notification)
        return START_NOT_STICKY
    }

    override fun onListenerConnected() {
        Log.e(null, "onListenerConnected")
        super.onListenerConnected()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) return

        val notification = ShantofyNotification.fromSbn(sbn)

        if (ruleBasedNotificationChecker.shouldBlock(notification)) {
            Log.d(null, "Blocked! $notification")
            this.cancelNotification(sbn.key)
            updateNotification(incrementCount())
        }

        super.onNotificationPosted(sbn)
    }

    private fun registerNotificationChannel() {
        val channel = NotificationChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_human),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        channel.setSound(null, null)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, FLAG_MUTABLE)
        val content = if (!isNotificationServiceEnabled()) {
            buildNotReadyContent()
        } else {
            buildNotificationContent(getCount())
        }
        return content
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun updateNotification(count: Int) {
        val notification = buildNotificationContent(count).build()

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(NOTIFICATION_CHANNEL_ID, notification);
    }

    private fun buildNotReadyContent() =
        Notification.Builder(this, getString(R.string.notification_channel_id))
            .setContentTitle(getString(R.string.notification_listener_service))
            .setContentText(getString(R.string.notification_listener_service_not_ready))
            .setSmallIcon(R.drawable.ic_notification_icon)

    private fun buildNotificationContent(count: Int) =
        Notification.Builder(this, getString(R.string.notification_channel_id))
            .setContentTitle(getString(R.string.notification_listener_service))
            .setContentText(getString(R.string.notification_listener_service_content, count))
            .setSmallIcon(R.drawable.ic_notification_icon)

    private fun incrementCount(): Int {
        val editor: SharedPreferences.Editor =
            getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE).edit()
        val newCount = getCount() + 1
        editor.putInt(getString(R.string.block_count_key), newCount)
        editor.apply()
        return newCount
    }

    private fun getCount(): Int {
        val prefs: SharedPreferences =
            getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE)
        return prefs.getInt(getString(R.string.block_count_key), 0)
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val flat: String = Settings.Secure.getString(
            contentResolver,
            enabledNotificationListeners
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":").toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(packageName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = 1
    }
}
