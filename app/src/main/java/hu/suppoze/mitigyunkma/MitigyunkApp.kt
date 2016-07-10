package hu.suppoze.mitigyunkma

import android.app.Application
import hu.suppoze.mitigyunkma.util.ResourceHelper
import io.realm.Realm
import io.realm.RealmConfiguration

class MitigyunkApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ResourceHelper.registerApp(this)
        val config = RealmConfiguration.Builder(this).build()

        if (BuildConfig.BUILD_TYPE == "debug") {
            // Debug
        } else {
            // Relese
        }

        Realm.setDefaultConfiguration(config)
    }
}