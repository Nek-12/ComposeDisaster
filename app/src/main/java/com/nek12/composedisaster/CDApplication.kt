package com.nek12.composedisaster

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CDApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
            androidContext(applicationContext)
            createEagerInstances()
        }
    }
}
