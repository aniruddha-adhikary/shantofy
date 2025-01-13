package net.adhikary.thamen.core

import android.service.notification.StatusBarNotification

data class ShantofyNotification(
    val title: String?,
    val text: String?,
    val template: String?,
    val sbn: StatusBarNotification,
) {
    override fun toString(): String {
        return "ShantofyNotification(title=$title, text=$text, template=$template)"
    }

    companion object {
        fun fromSbn(sbn: StatusBarNotification): ShantofyNotification {
            return ShantofyNotification(
                title = sbn.notification.extras.getString(ExtraFields.Title.attrName),
                text = sbn.notification.extras.getString(ExtraFields.Text.attrName),
                template = sbn.notification.extras.getString(ExtraFields.Template.attrName),
                sbn = sbn,
            )
        }
    }
}
