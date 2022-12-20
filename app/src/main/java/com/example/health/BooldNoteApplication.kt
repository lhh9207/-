package com.ssionii.bloodNote

import android.app.Application
import com.ssionii.bloodNote.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BloodNoteApplication : Application(){



    companion object {
        private lateinit var instance: BloodNoteApplication

        fun getGlobalApplicationContext(): BloodNoteApplication {
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger()
            androidContext(this@BloodNoteApplication)
            modules(appModule)
        }

    }
}