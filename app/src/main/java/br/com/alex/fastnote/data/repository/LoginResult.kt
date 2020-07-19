package br.com.alex.fastnote.data.repository

import br.com.alex.fastnote.data.db.LoggedUser

sealed class LoginResult {
    class Success(val loggedUser: LoggedUser?): LoginResult()
    object NotFound : LoginResult()
    object Error: LoginResult()
}