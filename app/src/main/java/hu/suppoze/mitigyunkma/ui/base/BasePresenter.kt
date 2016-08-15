package hu.suppoze.mitigyunkma.ui.base

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