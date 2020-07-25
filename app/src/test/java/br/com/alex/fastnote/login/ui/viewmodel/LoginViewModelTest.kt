package br.com.alex.fastnote.login.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.alex.fastnote.login.data.model.User
import br.com.alex.fastnote.login.data.repository.LoginRepository
import br.com.alex.fastnote.login.ui.view.LoginFormState
import br.com.alex.fastnote.login.ui.view.LoginResultView
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var repository: LoginRepository

    private val loginResultObserver: Observer<LoginResultView> = mock()
    private val loadingObserver: Observer<Boolean> = mock()
    private val loginFormStateObserver: Observer<LoginFormState> = mock()
    private val savedLoginObserver: Observer<Pair<Boolean, User>> = mock()

    @Before
    fun setup(){
        viewModel = LoginViewModel(repository)

        viewModel.loginResult.observeForever(loginResultObserver)
        viewModel.loading.observeForever(loadingObserver)
        viewModel.loginFormState.observeForever(loginFormStateObserver)
        viewModel.savedLogin.observeForever(savedLoginObserver)
    }

    @Test
    fun `assert if view model observers is not null`() {
        Assert.assertNotNull(viewModel.loginResult)
        Assert.assertNotNull(viewModel.loading)
        Assert.assertNotNull(viewModel.loginFormState)
        Assert.assertNotNull(viewModel.savedLogin)
    }
}