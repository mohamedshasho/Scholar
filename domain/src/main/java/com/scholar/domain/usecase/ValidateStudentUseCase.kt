package com.scholar.domain.usecase

import com.scholar.domain.model.LoginInputValidationType

class ValidateStudentUseCase {

    operator fun invoke(
        name: String,
        image: String,
        phone: String,
        brith: String
    ): LoginInputValidationType {
        return if (name.isEmpty() || image.isEmpty() || phone.isEmpty() || brith.isEmpty()) {
            LoginInputValidationType.EmptyField
        } else {
            LoginInputValidationType.Valid
        }
    }
}