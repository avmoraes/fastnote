package br.com.alex.fastnote.login.data.repository.datasource

import br.com.alex.fastnote.login.data.repository.LoginResult

interface LoginDataSource {

    suspend fun login(email: String, password: String, loginResult: (loginResult: LoginResult) -> Unit)

    suspend fun logout()
}