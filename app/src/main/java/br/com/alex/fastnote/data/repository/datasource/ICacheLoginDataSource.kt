package br.com.alex.fastnote.data.repository.datasource

import br.com.alex.fastnote.data.model.User

const val USER_SHARED = "user_login"
const val USER_EMAIL_KEY = "user_email"
const val USER_PASSWORD_KEY = "user_password"

interface ICacheLoginDataSource {
    suspend fun saveOrUpdateUser(user: User)
    suspend fun getUser(): User
    suspend fun isSavedLogin(): Boolean
    suspend fun clearSavedLogin()
}