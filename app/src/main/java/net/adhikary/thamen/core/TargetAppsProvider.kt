package net.adhikary.thamen.core

interface TargetAppsProvider {
    fun getApp(packageId: String): TargetApp?
}