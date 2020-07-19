package br.com.alex.fastnote.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alex.fastnote.R
import br.com.alex.fastnote.data.repository.LoginRepository
import br.com.alex.fastnote.data.repository.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun login(email: String, password: String){
        _loading.value = true
        CoroutineScope(Dispatchers.Main).launch {
            loginRepository.login(email, password) { loginResult ->
                _loading.postValue(false)
                when(loginResult) {
                    is LoginResult.Success -> {
                        _loginResult.postValue(loginResult.loggedUser?.let { loggedUser ->
                            LoginResultView.Success(R.string.welcome,
                                loggedUser
                            )
                        })
                    }
                    is LoginResult.NotFound -> {
                        _loginResult.postValue(LoginResultView.NotFound(R.string.login_failed))
                    }
                    is LoginResult.Error -> {
                        _loginResult.postValue(LoginResultView.Error(R.string.login_failed))
                    }
                }
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginState.value = LoginFormState.UserNameError(R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginState.value = LoginFormState.PasswordError(R.string.invalid_password)
        } else {
            _loginState.value = LoginFormState.IsDataValid()
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
}