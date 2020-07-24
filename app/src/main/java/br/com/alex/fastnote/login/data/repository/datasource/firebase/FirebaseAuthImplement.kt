package br.com.alex.fastnote.login.data.repository.datasource.firebase

import br.com.alex.fastnote.login.data.repository.LoginResult
import com.google.firebase.auth.FirebaseAuth
import br.com.alex.fastnote.login.data.db.LoggedUser

class FirebaseAuthImplement(private val firebaseAuth: FirebaseAuth): Auth {

    override suspend fun loginByEmail(
        email: String,
        password: String,
        loginResult: (loginResult: LoginResult) -> Unit
    ) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        val user = firebaseAuth.currentUser

                        var loggedUser = LoggedUser(user?.displayName, user?.email, user?.photoUrl)

                        loginResult(LoginResult.Success(loggedUser))
                    }
                    else -> {
                        loginResult(LoginResult.NotFound)
                    }
                }
            }
        } catch (e: Throwable) {
            loginResult(LoginResult.Error)
        }
    }

}