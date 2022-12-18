package net.adhikary.shantofy.core

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
                title = sbn.notification.extras.getString(ExtraFields.title.attrName),
                text = sbn.notification.extras.getString(ExtraFields.text.attrName),
                template = sbn.notification.extras.getString(ExtraFields.template.attrName),
                sbn = sbn,
            )
        }
    }
}
