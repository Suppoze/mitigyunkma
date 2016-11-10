package hu.suppoze.mitigyunkma

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import hu.suppoze.mitigyunkma.di.AppComponent
import hu.suppoze.mitigyunkma.di.AppModule
import hu.suppoze.mitigyunkma.di.DaggerAppComponent

class MitigyunkApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initializeLeakCanary()
        initializeInjector()
    }

    private fun initializeLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    private fun initializeInjector() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this, getString(R.string.default_drink_name)))
                .build()
    }
}