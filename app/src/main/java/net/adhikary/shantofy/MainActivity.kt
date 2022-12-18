package net.adhikary.shantofy

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import net.adhikary.shantofy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val enabledNotificationListeners = "enabled_notification_listeners"
    private val ACTION_NOTIFICATION_LISTENER_SETTINGS =
        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    private var enableNotificationListenerAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerService()
    }

    private fun registerService() {
        if (!isNotificationServiceEnabled()) {
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog?.show()
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat: String = Settings.Secure.getString(
            contentResolver,
            enabledNotificationListeners
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":").toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog? {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.notification_listener_service)
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation)
        alertDialogBuilder.setPositiveButton(
            R.string.yes
        ) { dialog, id -> startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)) }
        alertDialogBuilder.setNegativeButton(
            R.string.no
        ) { dialog, id ->
            // If you choose to not enable the notification listener
            // the app. will not work as expected
        }
        return alertDialogBuilder.create()
    }
}