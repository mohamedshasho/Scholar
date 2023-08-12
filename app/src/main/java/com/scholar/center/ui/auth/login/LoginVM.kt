package com.scholar.center.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.R
import com.scholar.domain.model.LoginInputValidationType
import com.scholar.domain.model.LoginState
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.AuthRepository
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.usecase.ValidateLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val dataStore: DataStorePreference,
    private val authRepository: AuthRepository,
    private val validateLoginUseCase: ValidateLoginUseCase,
) : ViewModel() {
    var loginState = MutableStateFlow(LoginState())
        private set


    fun onEmailInputChanged(newValue: String) {
        loginState.update { currentState ->
            currentState.copy(emailInput = newValue)
        }
        checkInputValidation()
    }

    fun onPasswordInputChanged(newValue: String) {
        loginState.update { currentState ->
            currentState.copy(passwordInput = newValue)
        }
        checkInputValidation()
    }

    fun onToggleVisualTransformation() {
        loginState.update { currentState ->
            currentState.copy(isPasswordShown = !currentState.isPasswordShown)
        }
    }

    private fun checkInputValidation() {
        val validation = validateLoginUseCase(
            email = loginState.value.emailInput,
            password = loginState.value.passwordInput
        )
        processInputValidationType(validation)
    }

    fun onLoginClick() {
        if (!loginState.value.isInputValid) return
        if (loginState.value.isLoading) return

        loginState.update { currentState ->
            currentState.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = authRepository.login(
                email = loginState.value.emailInput,
                password = loginState.value.passwordInput,
            )
            when (result) {
                is Resource.Success -> {
                    result.data?.let { studentID ->
                        dataStore.saveValue(DataStorePreference.userId, studentID)
                        dataStore.saveValue(DataStorePreference.isUserLoggedIn, true)

                        loginState.update { currentState ->
                            currentState.copy(isSuccessfullyLogin = true, isLoading = false)
                        }
                    }
                }
                is Resource.Error -> {
                    loginState.update { currentState ->
                        currentState.copy(
                            errorMessageLoginProcess = result.message,
                            isLoading = false
                        )
                    }
                }
            }

        }
    }

    private fun processInputValidationType(type: LoginInputValidationType) {
        loginState.update { currentState ->
            when (type) {
                LoginInputValidationType.EmptyField -> {
                    currentState.copy(
                        errorMessageInput = R.string.empty_fields_left,
                        isInputValid = false
                    )
                }
                LoginInputValidationType.NonEmail -> {
                    currentState.copy(
                        errorMessageInput = R.string.no_valid_email,
                        isInputValid = false
                    )
                }
                LoginInputValidationType.Valid -> {
                    currentState.copy(errorMessageInput = null, isInputValid = true)
                }
            }
        }
    }

}