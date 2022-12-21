package net.adhikary.shantofy

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import net.adhikary.shantofy.core.RuleBasedNotificationChecker
import net.adhikary.shantofy.core.ShantofyNotification


class ShantofyNotificationListenerService : NotificationListenerService() {

    private val ruleBasedNotificationChecker = RuleBasedNotificationChecker();

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
        registerNotificationChannel()
        val notification =
            NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(getString(R.string.notification_listener_service))
                .setContentText(getString(R.string.notification_listener_service_content))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1, notification)
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
            this.cancelNotification(sbn.key)
        }

        super.onNotificationPosted(sbn)
    }

    private fun registerNotificationChannel() {
        val channel = NotificationChannel(
            getString(R.string.notification_channel_id),
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}