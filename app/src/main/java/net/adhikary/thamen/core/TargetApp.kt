package net.adhikary.thamen.core

data class TargetApp(
    val packageName: String,
    val blockChannels: Set<String>? = null
) {
    override fun toString(): String {
        return "TargetApp(packageName='$packageName')"
    }
}