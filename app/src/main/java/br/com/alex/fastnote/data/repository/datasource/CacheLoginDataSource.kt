package br.com.alex.fastnote.data.repository.datasource

import android.content.SharedPreferences
import br.com.alex.fastnote.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CacheLoginDataSource(private val sharedPreferences: SharedPreferences): ICacheLoginDataSource {
    override suspend fun saveOrUpdateUser(user: User) {
       // withContext(Dispatchers.IO) {
            sharedPreferences.edit().putString(USER_EMAIL_KEY, user.email).apply()
            sharedPreferences.edit().putString(USER_PASSWORD_KEY, user.password).apply()
        //}
    }

    override suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            val email = sharedPreferences.getString(USER_EMAIL_KEY, "") ?: ""
            val password = sharedPreferences.getString(USER_PASSWORD_KEY, "") ?: ""

            User(email, password)
        }
    }

    override suspend fun isSavedLogin(): Boolean {
        return withContext(Dispatchers.IO) {
            sharedPreferences.contains(USER_EMAIL_KEY) && sharedPreferences.contains(USER_PASSWORD_KEY)
        }
    }

    override suspend fun clearSavedLogin() {
        sharedPreferences.edit().remove(USER_EMAIL_KEY).apply()
        sharedPreferences.edit().remove(USER_PASSWORD_KEY).apply()
    }
}