package br.com.alex.fastnote.data.repository.datasource.firebase

import br.com.alex.fastnote.data.model.User
import br.com.alex.fastnote.data.repository.LoginResult

interface Auth {
    suspend fun loginByEmail(email: String, password: String, loginResult: (loginResult:LoginResult) -> Unit)
}