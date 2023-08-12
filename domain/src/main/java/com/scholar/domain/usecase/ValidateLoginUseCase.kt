package com.scholar.domain.usecase

import com.scholar.domain.model.LoginInputValidationType

class ValidateLoginUseCase {

    operator fun invoke(email: String, password: String): LoginInputValidationType {
        return if (email.isEmpty() || password.isEmpty()) {
            LoginInputValidationType.EmptyField
        } else if ("@" !in email) {
            LoginInputValidationType.NonEmail
        } else {
            LoginInputValidationType.Valid
        }
    }
}