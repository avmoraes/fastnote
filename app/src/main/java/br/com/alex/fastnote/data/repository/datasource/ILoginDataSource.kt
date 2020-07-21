package br.com.alex.fastnote.data.repository.datasource

import br.com.alex.fastnote.data.repository.LoginResult

interface ILoginDataSource {

    suspend fun login(email: String, password: String, loginResult: (loginResult: LoginResult) -> Unit)

    suspend fun logout()
}