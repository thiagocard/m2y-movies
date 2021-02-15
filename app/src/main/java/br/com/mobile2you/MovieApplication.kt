package br.com.mobile2you

import android.app.Application
import br.com.mobile2you.di.appModule
import br.com.mobile2you.di.mappersModule
import br.com.mobile2you.di.tmdbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MovieApplication)
            modules(appModule, mappersModule, tmdbModule)
        }

    }
}