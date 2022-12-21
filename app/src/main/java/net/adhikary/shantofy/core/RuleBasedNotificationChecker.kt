package net.adhikary.shantofy.core

class RuleBasedNotificationChecker : NotificationChecker {
    val contentBlacklist = listOf<Regex>(
        Regex("(অফার|offer|ডিসকাউন্ট|discount|ক্যাশব্যাক|cashback)", RegexOption.IGNORE_CASE),
        Regex("(%|টাকা|tk|taka|শতাংশ)\\s*(off|অফ|ছাড়)", RegexOption.IGNORE_CASE),
    )

    override fun shouldBlock(notification: ShantofyNotification): Boolean {
        val app = TargetApps.from(notification.sbn.packageName) ?: return false

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
            if (pattern.containsMatchIn(searchField))
                return@shouldBlockByContent true
        }
        return false
    }
}