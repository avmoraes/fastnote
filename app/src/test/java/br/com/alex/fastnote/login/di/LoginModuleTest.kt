package br.com.alex.fastnote.login.di

import br.com.alex.fastnote.login.data.repository.LoginRepository
import br.com.alex.fastnote.login.data.repository.datasource.CacheLoginDataSource
import br.com.alex.fastnote.login.data.repository.datasource.LoginDataSource
import br.com.alex.fastnote.login.data.repository.datasource.firebase.Auth
import br.com.alex.fastnote.mocks.firebaseMockModule
import br.com.alex.fastnote.mocks.prefsMockModule
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class LoginModuleTest: AutoCloseKoinTest() {

    @Before
    fun setup() {
        startKoin() {
            modules(prefsMockModule, firebaseMockModule, loginDependenciesModule)
        }
    }

    @Test
    fun `Assert If Auth Is provided by DI`(){
        val auth by inject<Auth>()
        Assert.assertNotNull(auth)
    }

    @Test
    fun `Assert If LoginDataSource Is provided by DI`(){
        val loginDataSource by inject<LoginDataSource>()
        Assert.assertNotNull(loginDataSource)
    }

    @Test
    fun `Assert If CacheLoginDataSource Is provided by DI`(){
        val cacheLoginDataSource by inject<CacheLoginDataSource>()
        Assert.assertNotNull(cacheLoginDataSource)
    }

    @Test
    fun `Assert If LoginRepository Is provided by DI`(){
        val loginRepository by inject<LoginRepository>()
        Assert.assertNotNull(loginRepository)
    }
}