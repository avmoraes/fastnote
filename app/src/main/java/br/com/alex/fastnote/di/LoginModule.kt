package br.com.alex.fastnote.di

import br.com.alex.fastnote.data.repository.datasource.LoginDataSource
import br.com.alex.fastnote.data.repository.LoginRepository
import br.com.alex.fastnote.data.repository.datasource.firebase.Auth
import br.com.alex.fastnote.data.repository.datasource.firebase.FirebaseAuthImplement
import br.com.alex.fastnote.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    factory <Auth>{
        FirebaseAuthImplement(firebaseAuth = FirebaseAuth.getInstance())
    }

    factory <LoginRepository>() {
        LoginDataSource(auth = get())
    }

    viewModel {
        LoginViewModel(loginRepository = get())
    }
}

