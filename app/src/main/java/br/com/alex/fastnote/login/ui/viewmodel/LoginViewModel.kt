package br.com.alex.fastnote.login.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alex.fastnote.R
import br.com.alex.fastnote.login.data.model.User
import br.com.alex.fastnote.login.data.repository.LoginRepository
import br.com.alex.fastnote.login.data.repository.LoginResult
import br.com.alex.fastnote.login.ui.view.LoginFormState
import br.com.alex.fastnote.login.ui.view.LoginResultView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {

    private val _loginResult by lazy { MutableLiveData<LoginResultView>() }
    val loginResult: LiveData<LoginResultView>
        get() = _loginResult

    private val _loading by lazy{ MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean>
        get() = _loading

    private val _loginState by lazy{ MutableLiveData<LoginFormState>() }
    val loginFormState: LiveData<LoginFormState>
        get() = _loginState

    private val _savedLogin by lazy{ MutableLiveData<Pair<Boolean, User>>() }
    val savedLogin: LiveData<Pair<Boolean, User>>
        get() = _savedLogin

    fun login(email: String, password: String, saveLogin: Boolean){
        _loading.value = true
        CoroutineScope(Dispatchers.Main).launch {
            loginRepository.login(email, password) { loginResult ->
                _loading.postValue(false)
                when(loginResult) {
                    is LoginResult.Success -> {
                        _loginResult.postValue(loginResult.loggedUser?.let { loggedUser ->
                            LoginResultView.Success(
                                R.string.welcome,
                                loggedUser
                            )
                        })
                        if(saveLogin) {
                            CoroutineScope(Dispatchers.Main).launch {
                                loginRepository.saveLogin(User(email, password))
                            }
                        } else {
                            CoroutineScope(Dispatchers.Main).launch {
                                loginRepository.clearSavedLogin()
                            }
                        }
                    }
                    is LoginResult.NotFound -> {
                        _loginResult.postValue(
                            LoginResultView.NotFound(
                                R.string.login_failed
                            )
                        )
                    }
                    is LoginResult.Error -> {
                        _loginResult.postValue(
                            LoginResultView.Error(
                                R.string.login_failed
                            )
                        )
                    }
                }
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginState.value =
                LoginFormState.UserNameError(
                    R.string.invalid_username
                )
        } else if (!isPasswordValid(password)) {
            _loginState.value =
                LoginFormState.PasswordError(
                    R.string.invalid_password
                )
        } else {
            _loginState.value =
                LoginFormState.IsDataValid()
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }

    fun getSavedLoginIfSaved() {
        CoroutineScope(Dispatchers.Main).launch {
            val savedLogin = loginRepository.isSavedLogin()

            if(savedLogin) {
                val user = loginRepository.getSavedLogin()
                _savedLogin.value = Pair(savedLogin, user)
            }
        }
    }

}