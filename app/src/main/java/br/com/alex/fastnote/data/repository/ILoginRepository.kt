package br.com.alex.fastnote.data.repository

import br.com.alex.fastnote.data.model.User

interface ILoginRepository {

    suspend fun login(email: String, password: String, loginResult: (loginResult: LoginResult) -> Unit)
    suspend fun logout()
    suspend fun saveLogin(user: User)
    suspend fun isSavedLogin(): Boolean
    suspend fun getSavedLogin(): User
    suspend fun clearSavedLogin()

}