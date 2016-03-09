package hu.suppoze.mitigyunkma

import android.app.Application
import hu.suppoze.mitigyunkma.util.ResourceHelper
import io.realm.Realm
import io.realm.RealmConfiguration

class MitigyunkApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ResourceHelper.registerApp(this)

        if (BuildConfig.BUILD_TYPE == "debug") {

//            val config = RealmConfiguration.Builder(this).build()
//            Realm.deleteRealm(config)

        } else {

        }
    }
}