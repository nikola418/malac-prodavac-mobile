package com.triforce.malacprodavac.presentation.registration

import com.triforce.malacprodavac.domain.util.enum.UserRole

sealed class RegistrationFormEvent {
    data class FirstNameChanged(val firstName: String) : RegistrationFormEvent()
    data class LastNameChanged(val lastName: String) : RegistrationFormEvent()
    data class RoleChanged(val role: UserRole) : RegistrationFormEvent()
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    data class RepeatedPasswordChanged
        (val repeatedPassword: String) : RegistrationFormEvent()

    data class AcceptTermsChanged(val isAccepted: Boolean) : RegistrationFormEvent()
    object Submit : RegistrationFormEvent()
}
