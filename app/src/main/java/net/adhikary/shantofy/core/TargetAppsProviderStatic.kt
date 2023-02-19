package net.adhikary.shantofy.core

class TargetAppsProviderStatic : TargetAppsProvider {
    override fun getApp(packageId: String): TargetApp? {
        return map.getOrDefault(packageId, null)
    }

    companion object {
        private val apps = listOf<TargetApp>(
            // e-commerce
            TargetApp("com.chaldal.poached"),
            TargetApp("com.daraz.android"),

            // ubers
            TargetApp(
                packageName = "com.ubercab",
                blockChannels = setOf("messsages_promo_recommend_channels")
            ),
            TargetApp("com.pathao.user"),
            TargetApp("com.obhai"),

            // banks
            TargetApp("com.cibl.eblsky"),
            TargetApp("com.mtb.mobilebanking"),
            TargetApp("com.thecitybank.citytouch"),
            TargetApp("com.bracbank.astha"),
            TargetApp("com.fsiblbd.fsiblcloud"),
            TargetApp("bd.com.primebank.pib.altitudemobile"),
            TargetApp("com.gplex.dhakabank"),
            TargetApp("bd.com.sonalibank.sonalie_sheba"),
            TargetApp("com.bengal.newdigitalbanking"),
            TargetApp("com.cibl.tbl"),
            TargetApp("com.sslwireless.nrbmobapp"),
            TargetApp("com.bd.aibl.ibapps"),

            // mfs
            TargetApp("com.bKash.customerapp"),
            TargetApp(
                packageName = "com.konasl.nagad",
                blockChannels = setOf("ANNOUNCEMENT")
            ),
            TargetApp("com.dbbl.mbs.apps.main"),
            TargetApp("bd.com.upay.customer"),
            TargetApp("com.tad.bdkepler"),

            // telco
            TargetApp("teletalk.teletalkcustomerapp"),
            TargetApp("net.omobio.airtelsc"),
            TargetApp("com.arena.banglalinkmela.app"),
            TargetApp("net.banglalink.mybllite"),
            TargetApp("com.portonics.mygp"),
            TargetApp("net.omobio.robisc"),

            // food
            TargetApp("com.dominos.bd"),
            TargetApp(
                packageName = "com.global.foodpanda.android",
                blockChannels = setOf("com.global.foodpanda.android_PROMOTIONS")
            ),
            TargetApp("com.aamra.kfc.bd.kfc_bd")
        )

        private val map = apps.associateBy { app -> app.packageName }
    }
}