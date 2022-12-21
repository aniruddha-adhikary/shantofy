package net.adhikary.shantofy.core

enum class TargetApps(val packageName: String, val blockChannels: Set<String>? = null) {
    // e-commerce
    Chaldal("com.chaldal.poached"),
    Daraz("com.daraz.android"),

    // ubers
    Uber("com.ubercab", setOf("messsages_promo_recommend_channels")),
    Pathao("com.pathao.user"),
    Obhai("com.obhai"),

    // banks
    EasternBankEBL("com.cibl.eblsky"),
    MutualTrustBankMTB("com.mtb.mobilebanking"),
    TheCityBank("com.thecitybank.citytouch"),

    // mfs
    Bkash("com.bKash.customerapp"),
    Nagad("com.konasl.nagad", setOf("ANNOUNCEMENT")),

    // telco
    MyGP("com.portonics.mygp");

    companion object {
        private val map = TargetApps.values().associateBy { app -> app.packageName }
        fun from(type: String?): TargetApps? = map.getOrDefault(type, null)
    }
}