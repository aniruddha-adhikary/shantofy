package net.adhikary.shantofy

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import net.adhikary.shantofy.core.RuleBasedNotificationChecker
import net.adhikary.shantofy.core.ShantofyNotification

class ShantofyNotificationListenerService : NotificationListenerService() {

    private val ruleBasedNotificationChecker = RuleBasedNotificationChecker();

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerNotificationChannel()
        val notification = createNotification(getCount())
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

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun createNotification(count: Int): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, FLAG_MUTABLE)
        return buildNotificationContent(count)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun updateNotification(count: Int) {
        val notification = buildNotificationContent(count).build()

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(NOTIFICATION_CHANNEL_ID, notification);
    }

    private fun buildNotificationContent(count: Int) =
        NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
            .setContentTitle(getString(R.string.notification_listener_service))
            .setContentText(getString(R.string.notification_listener_service_content, count))
            .setSmallIcon(R.mipmap.ic_launcher_round)

    private fun incrementCount(): Int {
        val editor: SharedPreferences.Editor =
            getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE).edit()
        val newCount = getCount() + 1
        editor.putInt(getString(R.string.block_count_key), newCount)
        Log.d(null, "Setting count $newCount")
        editor.apply()
        return newCount
    }

    private fun getCount(): Int {
        val prefs: SharedPreferences =
            getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE)
        val count = prefs.getInt(getString(R.string.block_count_key), 0)
        Log.d(null, "Returning count $count")
        return count
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = 1
    }
}