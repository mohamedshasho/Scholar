package com.scholar.domain.model

import androidx.annotation.StringRes


data class StudentState(
    val studentIdInput: Int? = null,
    val emailInput: String = "",
    val fullNameInput: String = "",
    val brithInput: String = "",
    val phoneInput: String = "",
    val imageInput: String = "",
    val image: String = "",
    val isInputValid: Boolean = false,
    @StringRes val errorMessageInput: Int? = null,
    val isLoading: Boolean = false,
    val isSuccessfullyLogin: Boolean = false,
    val errorMessageLoginProcess: String? = null,
)
