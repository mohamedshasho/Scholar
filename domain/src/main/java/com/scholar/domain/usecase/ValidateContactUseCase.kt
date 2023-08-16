package com.scholar.domain.usecase

import com.scholar.domain.model.LoginInputValidationType

class ValidateContactUseCase {

    operator fun invoke(
        name: String,
        email: String,
        phone: String,
        subject: String
    ): LoginInputValidationType {
        return if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || subject.isEmpty()) {
            LoginInputValidationType.EmptyField
        } else if ("@" !in email) {
            LoginInputValidationType.NonEmail
        } else {
            LoginInputValidationType.Valid
        }
    }
}