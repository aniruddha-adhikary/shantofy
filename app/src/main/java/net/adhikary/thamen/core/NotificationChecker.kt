package net.adhikary.thamen.core

interface NotificationChecker {
    fun shouldBlock(notification: ShantofyNotification): Boolean
}