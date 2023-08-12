package com.scholar.domain.model

import androidx.annotation.StringRes


data class LoginState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val isInputValid: Boolean = false,
    val isPasswordShown: Boolean = false,
    @StringRes val errorMessageInput: Int? = null,
    val isLoading: Boolean = false,
    val isSuccessfullyLogin: Boolean = false,
    val errorMessageLoginProcess: String? = null,
)
