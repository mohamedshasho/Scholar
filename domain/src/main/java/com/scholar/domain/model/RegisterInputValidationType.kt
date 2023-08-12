package com.scholar.domain.model

enum class RegisterInputValidationType {
    EmptyField,
    NonEmail,
    PasswordTooShort,
    PasswordsNotMatch,
    Valid
}