package shopinpager.wingstud.shopinpagerrider

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.activity.SplashActi
import shopinpager.wingstud.shopinpagerrider.extensions.clear
import shopinpager.wingstud.shopinpagerrider.extensions.defaultSharedPreferences

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        var app: App? = null
        fun get() = app!!
    }

    fun logout() {
        defaultSharedPreferences.clear()
        //  AppRoomDatabase.getDatabase(this@App).clearAllTables()
        AccountManager.clear()
        startActivity(Intent(this, SplashActi::class.java).apply {
            this.flags = FLAG_ACTIVITY_NEW_TASK
        })
    }
}