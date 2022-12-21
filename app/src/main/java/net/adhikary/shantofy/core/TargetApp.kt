package net.adhikary.shantofy.core

data class TargetApp(
    val packageName: String,
    val blockChannels: Set<String>? = null
) {
    override fun toString(): String {
        return "TargetApp(packageName='$packageName')"
    }
}