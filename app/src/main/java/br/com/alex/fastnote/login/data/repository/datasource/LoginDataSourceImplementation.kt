package br.com.alex.fastnote.login.data.repository.datasource

import br.com.alex.fastnote.login.data.repository.LoginResult
import br.com.alex.fastnote.login.data.repository.datasource.firebase.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginDataSourceImplementation(private val auth: Auth): LoginDataSource {
    override suspend fun login(email: String, password: String, loginResult: (loginResult: LoginResult) -> Unit) {
        withContext(Dispatchers.IO) {
            auth.loginByEmail(email, password, loginResult)
        }
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}