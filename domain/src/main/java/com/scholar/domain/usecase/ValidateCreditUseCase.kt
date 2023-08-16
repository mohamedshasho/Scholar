package com.scholar.domain.usecase

import com.scholar.domain.model.CreditInputValidationType

class ValidateCreditUseCase {
    operator fun invoke(
        amount: Int,
        method: Int,
        link: String,
    ): CreditInputValidationType {
        return if (amount == 0) {
            CreditInputValidationType.EmptyAmount
        } else if (method == 0) {
            CreditInputValidationType.NoMethod

        } else if (link.isEmpty()) {
            CreditInputValidationType.NoLink
        } else {
            CreditInputValidationType.Valid
        }
    }
}