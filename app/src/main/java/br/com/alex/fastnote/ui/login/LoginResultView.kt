package br.com.alex.fastnote.ui.login

import br.com.alex.fastnote.data.db.LoggedUser

sealed class LoginResultView {
    class Success(val success: Int?, val loggedUser: LoggedUser): LoginResultView()
    class NotFound(val notFound: Int?): LoginResultView()
    class Error(val error: Int?): LoginResultView()
}