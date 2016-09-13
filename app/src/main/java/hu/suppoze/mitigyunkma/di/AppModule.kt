package hu.suppoze.mitigyunkma.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import hu.suppoze.mitigyunkma.BuildConfig
import hu.suppoze.mitigyunkma.MitigyunkApp
import hu.suppoze.mitigyunkma.core.SharedPreferencesManager
import hu.suppoze.mitigyunkma.core.SharedPreferencesRepository
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module class AppModule(private val app: MitigyunkApp) {

    val SHARED_PREF_KEY = "GLOBAL_PREF_KEY"

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideApplication(): MitigyunkApp {
        return app
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(sharedPreferences: SharedPreferences): SharedPreferencesRepository {
        return SharedPreferencesManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRealm(application: MitigyunkApp): Realm {
        val config = RealmConfiguration.Builder(application).build()
        if (BuildConfig.BUILD_TYPE == "debug") {
            // Debug
        } else {
            // Relese
        }
        Realm.setDefaultConfiguration(config)
        return Realm.getDefaultInstance()
    }
}