package hu.suppoze.mitigyunkma.util
import hu.suppoze.mitigyunkma.MitigyunkApp

object ResourceHelper {

    lateinit var app: MitigyunkApp

    fun registerApp(app: MitigyunkApp) {
        this.app = app
    }

    fun getStringRes(resId: Int): String {
        return app.getString(resId)
    }

}