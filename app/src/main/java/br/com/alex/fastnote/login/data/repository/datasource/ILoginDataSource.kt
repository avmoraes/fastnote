package br.com.alex.fastnote.login.data.repository.datasource

import br.com.alex.fastnote.login.data.repository.LoginResult

interface ILoginDataSource {

    suspend fun login(email: String, password: String, loginResult: (loginResult: LoginResult) -> Unit)

    suspend fun logout()
}