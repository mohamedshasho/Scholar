package com.scholar.center.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.R
import com.scholar.domain.model.RegisterInputValidationType
import com.scholar.domain.model.RegisterState
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.AuthRepository
import com.scholar.domain.usecase.ValidateRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(
    private val validateRegisterUseCase: ValidateRegisterUseCase,
    private val authRepository: AuthRepository,
) : ViewModel() {
    var registerState = MutableStateFlow(RegisterState())
        private set


    fun onFullNameInputChanged(newValue: String) {
        registerState.update { currentState ->
            currentState.copy(fullNameInput = newValue)
        }
        checkInputValidation()
    }
    fun onEmailInputChanged(newValue: String) {
        registerState.update { currentState ->
            currentState.copy(emailInput = newValue)
        }
        checkInputValidation()
    }

    fun onPasswordInputChanged(newValue: String) {
        registerState.update { currentState ->
            currentState.copy(passwordInput = newValue)
        }
        checkInputValidation()
    }

    fun onPasswordRepeatedInputChanged(newValue: String) {
        registerState.update { currentState ->
            currentState.copy(passwordRepeatedInput = newValue)
        }
        checkInputValidation()
    }

    fun onToggleVisualTransformation() {
        registerState.update { currentState ->
            currentState.copy(isPasswordShown = !currentState.isPasswordShown)
        }
    }


    private fun checkInputValidation() {
        val validation = validateRegisterUseCase(
            fullName = registerState.value.fullNameInput,
            email = registerState.value.emailInput,
            password = registerState.value.passwordInput,
            passwordRepeated = registerState.value.passwordRepeatedInput
        )
        processInputValidationType(validation)
    }

    fun onRegisterClick() {
        if (!registerState.value.isInputValid) return
        if (registerState.value.isLoading) return

        registerState.update { currentState ->
            currentState.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = authRepository.register(
                fullName = registerState.value.fullNameInput,
                email = registerState.value.emailInput,
                password = registerState.value.passwordInput,
            )
            when (result) {
                is Resource.Success -> {
                    result.data?.let { user ->
//                        dataStoreRepository.saveUser(user)
                        registerState.update { currentState ->
                            currentState.copy(isSuccessfullyRegister = true, isLoading = false)
                        }
                    }
                }
                is Resource.Error -> {
                    registerState.update { currentState ->
                        currentState.copy(
                            errorMessageRegisterProcess = result.message,
                            isLoading = false
                        )
                    }
                }
            }

        }
    }

    private fun processInputValidationType(type: RegisterInputValidationType) {
        registerState.update { currentState ->
            when (type) {
                RegisterInputValidationType.EmptyField -> {
                    currentState.copy(
                        errorMessageInput = R.string.empty_fields_left,
                        isInputValid = false
                    )
                }
                RegisterInputValidationType.NonEmail -> {
                    currentState.copy(
                        errorMessageInput = R.string.no_valid_email,
                        isInputValid = false
                    )
                }
                RegisterInputValidationType.PasswordTooShort -> {
                    currentState.copy(
                        errorMessageInput = R.string.password_too_short,
                        isInputValid = false
                    )
                }
                RegisterInputValidationType.PasswordsNotMatch -> {
                    currentState.copy(
                        errorMessageInput = R.string.passwords_not_match,
                        isInputValid = false
                    )
                }
                RegisterInputValidationType.Valid -> {
                    currentState.copy(errorMessageInput = null, isInputValid = true)
                }
            }
        }
    }

}