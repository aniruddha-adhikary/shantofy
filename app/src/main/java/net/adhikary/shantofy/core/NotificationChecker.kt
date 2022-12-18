package net.adhikary.shantofy.core

interface NotificationChecker {
    fun shouldBlock(notification: ShantofyNotification): Boolean
}