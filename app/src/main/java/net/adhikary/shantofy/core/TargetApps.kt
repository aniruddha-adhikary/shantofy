package net.adhikary.shantofy.core

enum class TargetApps(val packageName: String, val blockChannels: Set<String>? = null) {
    // e-commerce
    chaldal("com.chaldal.poached"),
    daraz("com.daraz.android"),

    // ubers
    uber("com.ubercab", setOf("messages_promo_recommend_channels")),
    pathao("com.pathao.user"),
    obhai("com.obhai"),

    // banks
    easternBankEbl("com.cibl.eblsky"),
    mutualTrustBank("com.mtb.mobilebanking"),
    thecitybank("com.thecitybank.citytouch"),

    // mfs
    bKash("com.bKash.customerapp"),
    nagad("com.konasl.nagad", setOf("ANNOUNCEMENT")),

    // telco
    myGp("com.portonics.mygp");

    companion object {
        private val map = TargetApps.values().associateBy { app -> app.packageName }
        fun from(type: String?): TargetApps? = map.getOrDefault(type, null)
    }
}