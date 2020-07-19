package br.com.alex.fastnote.data.repository.datasource

import br.com.alex.fastnote.data.model.User
import br.com.alex.fastnote.data.repository.LoginRepository
import br.com.alex.fastnote.data.repository.LoginResult
import br.com.alex.fastnote.data.repository.datasource.firebase.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginDataSource(private val auth: Auth): LoginRepository {
    override suspend fun login(email: String, password: String, loginResult: (loginResult: LoginResult) -> Unit) {
        withContext(Dispatchers.IO) {
            auth.loginByEmail(email, password, loginResult)
        }
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}