package net.adhikary.shantofy.core

interface TargetAppsProvider {
    fun getApp(packageId: String): TargetApp?
}