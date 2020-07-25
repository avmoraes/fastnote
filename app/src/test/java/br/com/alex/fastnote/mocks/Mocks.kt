package br.com.alex.fastnote.mocks

import android.content.SharedPreferences
import br.com.alex.fastnote.login.data.repository.datasource.firebase.Auth
import com.nhaarman.mockitokotlin2.mock
import org.koin.dsl.module

val prefsMockModule = module {
    single <SharedPreferences> { mock() }
}

val firebaseMockModule = module {
    factory<Auth> { mock() }
}
