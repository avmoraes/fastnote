package br.com.alex.fastnote.ui.login

sealed class LoginFormState {
    class UserNameError(val userNameError: Int?): LoginFormState()
    class PasswordError(val passwordError: Int?): LoginFormState()
    class IsDataValid(): LoginFormState()
}