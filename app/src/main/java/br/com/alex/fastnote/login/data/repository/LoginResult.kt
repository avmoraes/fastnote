package br.com.alex.fastnote.login.data.repository

import br.com.alex.fastnote.login.data.db.LoggedUser

sealed class LoginResult {
    class Success(val loggedUser: LoggedUser?): LoginResult()
    object NotFound : LoginResult()
    object Error: LoginResult()
}