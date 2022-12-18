package net.adhikary.shantofy

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import net.adhikary.shantofy.core.RuleBasedNotificationChecker
import net.adhikary.shantofy.core.ShantofyNotification


class ShantofyNotificationListenerService : NotificationListenerService() {

    private val ruleBasedNotificationChecker = RuleBasedNotificationChecker();

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
}