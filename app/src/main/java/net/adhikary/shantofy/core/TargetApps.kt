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
    BracBank("com.bracbank.astha"),
    FSIB("com.fsiblbd.fsiblcloud"),
    PrimeBank("bd.com.primebank.pib.altitudemobile"),
    DhakaBank("com.gplex.dhakabank"),
    SonaliBank("bd.com.sonalibank.sonalie_sheba"),
    Bengal("com.bengal.newdigitalbanking"),
    TrustBank("com.cibl.tbl"),
    NRBBank("com.sslwireless.nrbmobapp"),
    AIBL("com.bd.aibl.ibapps"),

    // mfs
    Bkash("com.bKash.customerapp"),
    Nagad("com.konasl.nagad", setOf("ANNOUNCEMENT")),
    Rocket("com.dbbl.mbs.apps.main"),
    Upay("bd.com.upay.customer"),
    Tap("com.tad.bdkepler"),

    // telco
    Teletalk("teletalk.teletalkcustomerapp"),
    Airtel("net.omobio.airtelsc"),
    Banglalink("com.arena.banglalinkmela.app"),
    BanglalinkLite("net.banglalink.mybllite"),
    MyGP("com.portonics.mygp"),

    // food
    Dominos("com.dominos.bd"),
    Foodpanda("com.global.foodpanda.android"),
    KFC("com.aamra.kfc.bd.kfc_bd");

    companion object {
        private val map = TargetApps.values().associateBy { app -> app.packageName }
        fun from(type: String?): TargetApps? = map.getOrDefault(type, null)
    }
}