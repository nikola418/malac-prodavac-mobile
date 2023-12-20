package com.triforce.malacprodavac.domain.use_case.validate

import java.util.regex.Pattern

class ValidateAddress {
    fun execute(address: String): ValidationResult {
        if (address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Adresa ne može biti prazna"
            )
        }
        if (!Pattern.compile(
                "^[\\p{L}[0-9],.'-]+$",
                Pattern.CASE_INSENSITIVE
            )
                .matcher(address).matches()
        ) {
            return ValidationResult(
                successful = false,
                errorMessage = "Adresa može sadržati samo slova, zarez, tačku, apostrof i srednju crtu!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}