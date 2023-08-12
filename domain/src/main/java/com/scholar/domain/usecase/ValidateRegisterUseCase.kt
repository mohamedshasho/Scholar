package com.scholar.domain.usecase

import com.scholar.domain.model.RegisterInputValidationType


class ValidateRegisterUseCase {
    operator fun invoke(
        fullName: String,
        email: String,
        password: String,
        passwordRepeated: String,
    ): RegisterInputValidationType {
        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            return RegisterInputValidationType.EmptyField
        }
        if ("@" !in email) {
            return RegisterInputValidationType.NonEmail
        }
        if (password.length < 6) {
            return RegisterInputValidationType.PasswordTooShort
        }
        if (password != passwordRepeated) {
            return RegisterInputValidationType.PasswordsNotMatch
        }
        return RegisterInputValidationType.Valid
    }
}