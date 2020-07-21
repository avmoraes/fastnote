package br.com.alex.fastnote.data.repository

interface ILoginRepository {

    suspend fun login(email: String, password: String, loginResult: (loginResult: LoginResult) -> Unit)

    suspend fun logout()
}