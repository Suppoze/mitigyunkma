package hu.suppoze.mitigyunkma.modules.base

import io.realm.Realm

open class BasePresenter {

    lateinit var realm: Realm

    fun onViewCreated() {
        realm = Realm.getDefaultInstance()
    }

    fun onViewDestroyed() {
        realm.close()
    }

}