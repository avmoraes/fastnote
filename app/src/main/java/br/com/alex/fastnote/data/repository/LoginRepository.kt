package br.com.alex.fastnote.data.repository

import br.com.alex.fastnote.data.repository.datasource.ILoginDataSource

class LoginRepository(private val loginDataSource: ILoginDataSource): ILoginRepository {
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

}