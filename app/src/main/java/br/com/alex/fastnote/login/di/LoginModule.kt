package br.com.alex.fastnote.login.di

import android.content.Context
import android.content.SharedPreferences
import br.com.alex.fastnote.login.data.repository.LoginRepository
import br.com.alex.fastnote.login.data.repository.LoginRepositoryImplementation
import br.com.alex.fastnote.login.data.repository.datasource.*
import br.com.alex.fastnote.login.data.repository.datasource.firebase.Auth
import br.com.alex.fastnote.login.data.repository.datasource.firebase.FirebaseAuthImplementation
import br.com.alex.fastnote.login.ui.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val prefsModule = module {
    single <SharedPreferences> { androidContext().getSharedPreferences(USER_SHARED, Context.MODE_PRIVATE) }
}

val authModule = module {
    factory <Auth>{
        FirebaseAuthImplementation(firebaseAuth = FirebaseAuth.getInstance())
    }
}

val loginDependenciesModule = module {
    factory <LoginDataSource>() {
        LoginDataSourceImplementation(auth = get())
    }

    factory <CacheLoginDataSource>() {
        CacheLoginDataSourceImplementation(get())
    }

    factory <LoginRepository>() {
        LoginRepositoryImplementation(loginDataSource = get(), cacheLoginDataSource = get())
    }
}

val loginModule = module {
    viewModel {
        LoginViewModel(loginRepository = get())
    }
}



