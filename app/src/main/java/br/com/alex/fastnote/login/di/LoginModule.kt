package br.com.alex.fastnote.login.di

import android.content.Context
import android.content.SharedPreferences
import br.com.alex.fastnote.login.data.repository.ILoginRepository
import br.com.alex.fastnote.login.data.repository.LoginRepository
import br.com.alex.fastnote.login.data.repository.datasource.*
import br.com.alex.fastnote.login.data.repository.datasource.firebase.Auth
import br.com.alex.fastnote.login.data.repository.datasource.firebase.FirebaseAuthImplement
import br.com.alex.fastnote.login.ui.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val prefsModule = module {
    single <SharedPreferences> { androidContext().getSharedPreferences(USER_SHARED, Context.MODE_PRIVATE) }
}

val loginDependenciesModule = module {
    factory <Auth>{
        FirebaseAuthImplement(firebaseAuth = FirebaseAuth.getInstance())
    }

    factory <ILoginDataSource>() {
        LoginDataSource(auth = get())
    }

    factory <ICacheLoginDataSource>() {
        CacheLoginDataSource(get())
    }

    factory <ILoginRepository>() {
        LoginRepository(loginDataSource = get(), cacheLoginDataSource = get())
    }
}

val loginModule = module {
    viewModel {
        LoginViewModel(loginRepository = get())
    }
}



