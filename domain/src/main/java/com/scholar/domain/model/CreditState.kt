package com.scholar.domain.model

import androidx.annotation.StringRes


data class CreditState(
    val amountInput: Int = 0,
    val methodInput: Int = 0,
    val descriptionInput: String = "",
    val linkInput: String = "",
    val isInputValid: Boolean = false,
    @StringRes val errorMessageInput: Int? = null,
    val isLoading: Boolean = false,
    val isSuccessfullySend: Boolean = false,
    val errorMessageLoginProcess: String? = null,
)
