package com.scholar.domain.model

import androidx.annotation.StringRes


data class ContactState(
    val nameInput: String = "",
    val emailInput: String = "",
    val phoneInput: String = "",
    val subjectInput: String = "",
    val isInputValid: Boolean = false,
    @StringRes val errorMessageInput: Int? = null,
    val isLoading: Boolean = false,
    val isSuccessfullySend: Boolean = false,
    val errorMessageLoginProcess: String? = null,
)
