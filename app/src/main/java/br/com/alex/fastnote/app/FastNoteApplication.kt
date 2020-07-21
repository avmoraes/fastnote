package br.com.alex.fastnote.app

import android.app.Application
import br.com.alex.fastnote.di.loginDependenciesModule
import br.com.alex.fastnote.di.loginModule
import br.com.alex.fastnote.di.prefsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FastNoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@FastNoteApplication)
            modules(loginModule, prefsModule, loginDependenciesModule)
        }
    }
}