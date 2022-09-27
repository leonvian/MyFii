package com.lvc.meufi

import android.app.Application
import com.lvc.meufi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyFiiApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin {
            androidContext(this@MyFiiApp)
            androidLogger()
            modules(appModule)
        }
    }

}