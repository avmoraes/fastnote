package br.com.alex.fastnote.app

import android.app.Application
import br.com.alex.fastnote.login.di.authModule
import br.com.alex.fastnote.login.di.loginDependenciesModule
import br.com.alex.fastnote.login.di.loginModule
import br.com.alex.fastnote.login.di.prefsModule
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
            modules(authModule, loginModule, prefsModule, loginDependenciesModule)
        }
    }
}