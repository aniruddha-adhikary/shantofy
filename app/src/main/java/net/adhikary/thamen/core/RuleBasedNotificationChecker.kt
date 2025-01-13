package net.adhikary.thamen.core

import android.content.Context

class RuleBasedNotificationChecker(context: Context) : NotificationChecker {

    private val targetAppsProvider: TargetAppsProvider = TargetAppsProviderStatic(context)
    private val wordBlackList = listOf(
        "অফার",  "offer",
        "ডিসকাউন্ট",  "discount", "\bছাড়\b",
        "ক্যাশব্যাক", "cashback",
        "free",  "ফ্রী",  "ফ্রি",  "বিনামূল্য",
        "কুপন", "coupon",
        "শর্ত", "t&c", "term", "condition",
        "কূপন", "কুপন", "coupon",
        "deal", "ডিল", "বাজিমাত", "exciting",
        "বিস্তারিত",
        "promo", "প্রোমো", "প্রমো",
    )

    private val contentBlacklist = listOf(
        Regex(
            wordBlackList.joinToString(prefix = "(", separator = "|", postfix = ")"),
            RegexOption.IGNORE_CASE
        ),
        Regex("(%|টাকা|tk|taka|শতাংশ)\\s*(off|অফ|ছাড়)", RegexOption.IGNORE_CASE),
    )

    override fun shouldBlock(notification: ShantofyNotification): Boolean {
        val app = targetAppsProvider.getApp(notification.sbn.packageName) ?: return false

        if (shouldBlockByChannel(app.blockChannels, notification)) return true
        if (shouldBlockByNotificationType(notification)) return true
        if (shouldBlockByContent(notification)) return true

        return false
    }

    private fun shouldBlockByChannel(
        blockChannels: Set<String>?, notification: ShantofyNotification
    ): Boolean {
        val channelId = notification.sbn.notification.channelId
        return blockChannels?.contains(channelId) == true
    }

    private fun shouldBlockByNotificationType(
        notification: ShantofyNotification
    ): Boolean {
        val bigPictureStyleNotification = "BigPictureStyle"
        return notification.template?.contains(bigPictureStyleNotification) == true
    }

    private fun shouldBlockByContent(notification: ShantofyNotification): Boolean {
        val searchField = (notification.text ?: "") + (notification.title ?: "")

        contentBlacklist.forEach { pattern ->
            if (pattern.containsMatchIn(searchField)) return@shouldBlockByContent true
        }
        return false
    }
}
