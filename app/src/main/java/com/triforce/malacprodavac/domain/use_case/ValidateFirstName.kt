package com.triforce.malacprodavac.domain.use_case


class ValiStringFirstName {
    fun execute(firstName: String):ValidationResult {
        if(firstName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Ime ne može biti prazno"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}