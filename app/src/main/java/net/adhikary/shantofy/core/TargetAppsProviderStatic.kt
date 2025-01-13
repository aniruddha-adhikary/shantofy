package net.adhikary.shantofy.core

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TargetAppsProviderStatic(private val context: Context) : TargetAppsProvider {
    private val map: Map<String, TargetApp> by lazy { loadApps() }

    override fun getApp(packageId: String): TargetApp? {
        return map[packageId]
    }

    private fun loadApps(): Map<String, TargetApp> {
        val json = context.assets.open("target_apps.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val type = object : TypeToken<AppsData>() {}.type
        val data = gson.fromJson<AppsData>(json, type)
        return data.apps.associateBy { it.packageName }
    }

    private data class AppsData(val apps: List<TargetApp>)
}
