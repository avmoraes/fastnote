package br.com.alex.fastnote.login.data.repository

import br.com.alex.fastnote.login.data.model.User
import br.com.alex.fastnote.login.data.repository.datasource.ICacheLoginDataSource
import br.com.alex.fastnote.login.data.repository.datasource.ILoginDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val loginDataSource: ILoginDataSource, private val cacheLoginDataSource: ICacheLoginDataSource): ILoginRepository {
    override suspend fun login(
        email: String,
        password: String,
        loginResult: (loginResult: LoginResult) -> Unit
    ) {
        loginDataSource.login(email, password, loginResult)
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun saveLogin(user: User) {
        cacheLoginDataSource.saveOrUpdateUser(user)
    }

    override suspend fun isSavedLogin(): Boolean {
        return withContext(Dispatchers.IO) {
            cacheLoginDataSource.isSavedLogin()
        }
    }

    override suspend fun getSavedLogin(): User {
        return withContext(Dispatchers.IO) {
            cacheLoginDataSource.getUser()
        }
    }

    override suspend fun clearSavedLogin() {
        return withContext(Dispatchers.IO) {
            cacheLoginDataSource.clearSavedLogin()
        }
    }

}