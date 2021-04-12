package utils

import android.app.Application
import data.SUserDB

class SApplication: Application() {
    companion object {
        private lateinit var instance: SApplication

        fun getDB(): SUserDB = SUserDB.getDatabase(instance)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}