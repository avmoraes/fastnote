package br.com.alex.fastnote.login.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.alex.fastnote.login.data.model.User
import br.com.alex.fastnote.login.data.repository.LoginRepository
import br.com.alex.fastnote.login.ui.view.LoginFormState
import br.com.alex.fastnote.login.ui.view.LoginResultView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var repository: LoginRepository

    private val loginResultObserver: Observer<LoginResultView> = mock()
    private val loadingObserver: Observer<Boolean> = mock()
    private val loginFormStateObserver: Observer<LoginFormState> = mock()
    private val savedLoginObserver: Observer<Pair<Boolean, User>> = mock()

    @Captor
    lateinit var userInfoCaptor: ArgumentCaptor<Pair<Boolean, User>>

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)

        viewModel = LoginViewModel(repository)

        viewModel.loginResult.observeForever(loginResultObserver)
        viewModel.loading.observeForever(loadingObserver)
        viewModel.loginFormState.observeForever(loginFormStateObserver)
        viewModel.savedLogin.observeForever(savedLoginObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `assert if view model observers is not null`() {
        Assert.assertNotNull(viewModel.loginResult)
        Assert.assertNotNull(viewModel.loading)
        Assert.assertNotNull(viewModel.loginFormState)
        Assert.assertNotNull(viewModel.savedLogin)
    }

    @Test
    fun `When view model getSavedLoginIfSaved get user then set user`() = runBlockingTest {
        // Arrange
        val user = User("fastnotetest@email.com", "123456")
        val result = Pair(true, user)

        whenever(repository.isSavedLogin()).thenReturn(true)
        whenever(repository.getSavedLogin()).thenReturn(user)

        // Act
        viewModel.getSavedLoginIfSaved()

        // Assert
        userInfoCaptor.run {
            verify(savedLoginObserver, times(1)).onChanged(capture())
            assertEquals(result, value)
        }
    }

}