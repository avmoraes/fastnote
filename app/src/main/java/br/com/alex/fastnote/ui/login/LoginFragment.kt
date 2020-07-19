package br.com.alex.fastnote.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alex.fastnote.R
import br.com.alex.fastnote.commons.afterTextChanged
import br.com.alex.fastnote.commons.hideKeyBoard
import kotlinx.android.synthetic.main.login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var rootview: View
    private lateinit var loginState: LoginFormState

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootview = inflater.inflate(R.layout.login, container, false)
        return rootview
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        login.setOnClickListener {
            viewModel.login(username.text.toString(), password.text.toString())
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer { visible ->
            activity?.hideKeyBoard()
            if(visible) {
                loading.visibility = View.VISIBLE
                login.isEnabled = false
            } else {
                loading.visibility = View.GONE
                if (loginState is LoginFormState.IsDataValid) {
                    login.isEnabled = true
                }
            }
        })

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { loginResult ->
            when(loginResult) {
                is LoginResultView.Success -> {
                    loginResult.success?.let { success ->
                        Toast.makeText( activity, success, Toast.LENGTH_LONG).show()
                    }
                }
                is LoginResultView.NotFound -> {
                    loginResult.notFound?.let { notFound ->
                        Toast.makeText( activity, notFound, Toast.LENGTH_LONG).show()
                    }
                }
                is LoginResultView.Error -> {
                    loginResult.error?.let { error ->
                        Toast.makeText( activity, error, Toast.LENGTH_LONG).show()
                    }
                }
            }

        })

        viewModel.loginFormState.observe(viewLifecycleOwner, Observer { loginState ->
            this.loginState = loginState
            when(loginState) {
                is LoginFormState.UserNameError -> {
                    username.error = loginState.userNameError?.let { getString(it) }
                    login.isEnabled = false
                }
                is LoginFormState.PasswordError -> {
                    password.error = loginState.passwordError?.let { getString(it) }
                    login.isEnabled = false
                }
                is LoginFormState.IsDataValid -> {
                    login.isEnabled = true
                }
            }
        })

        username.afterTextChanged {
            viewModel.loginDataChanged(username.text.toString(), password.text.toString())
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(username.text.toString(), password.text.toString())
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.login(username.text.toString(), password.text.toString())
                }
                false
            }
        }
    }
}