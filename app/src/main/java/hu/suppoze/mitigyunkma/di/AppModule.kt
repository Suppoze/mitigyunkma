package hu.suppoze.mitigyunkma.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import hu.suppoze.mitigyunkma.BuildConfig
import hu.suppoze.mitigyunkma.MitigyunkApp
import hu.suppoze.mitigyunkma.core.DrinkManager
import hu.suppoze.mitigyunkma.core.DrinkRepository
import hu.suppoze.mitigyunkma.core.SharedPreferencesManager
import hu.suppoze.mitigyunkma.core.SharedPreferencesRepository
import io.realm.Realm
import javax.inject.Named
import javax.inject.Singleton

@Module class AppModule(private val app: MitigyunkApp, private val defaultDrinkName: String) {

    @Provides @Named("SharedPreferencesKey") fun provideSharedPreferencesKey(): String = "SHARED_PREFERENCES_KEY"

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
    fun provideSharedPreferences(context: Context, @Named("SharedPreferencesKey") prefKey: String): SharedPreferences {
        return context.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(sharedPreferences: SharedPreferences): SharedPreferencesRepository {
        return SharedPreferencesManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRealm(application: MitigyunkApp): Realm {
        Realm.init(application)

        if (BuildConfig.BUILD_TYPE == "debug") {
            // Debug
        } else {
            // Relese
        }

        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun provideDrinkRepository(realm: Realm, sharedPreferencesRepository: SharedPreferencesRepository): DrinkRepository {
        return DrinkManager(realm, sharedPreferencesRepository, defaultDrinkName)
    }
}