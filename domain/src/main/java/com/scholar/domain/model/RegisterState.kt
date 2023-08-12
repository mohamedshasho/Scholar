package com.scholar.domain.model

import androidx.annotation.StringRes


data class RegisterState(
    val fullNameInput: String = "",
    val emailInput: String = "",
    val passwordInput: String = "",
    val passwordRepeatedInput: String = "",
    val isInputValid: Boolean = false,
    val isPasswordShown: Boolean = false,
    val isPasswordRepeatedShown: Boolean = false,
    @StringRes val errorMessageInput: Int? = null,
    val isLoading: Boolean = false,
    val isSuccessfullyRegister: Boolean = false,
    val errorMessageRegisterProcess: String? = null,
)
