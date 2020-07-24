package br.com.alex.fastnote.login.data.repository.datasource.firebase

import br.com.alex.fastnote.login.data.repository.LoginResult

interface Auth {
    suspend fun loginByEmail(email: String, password: String, loginResult: (loginResult:LoginResult) -> Unit)
}