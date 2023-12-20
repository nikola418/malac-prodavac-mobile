package com.triforce.malacprodavac.presentation.profile.profilePrivate

import android.content.Context
import android.net.Uri

sealed class ProfilePrivateEvent {
    object Logout : ProfilePrivateEvent()
    object Refresh : ProfilePrivateEvent()
    data object Edit : ProfilePrivateEvent()
    data class ChangeProfilePicture(val uri: Uri, val context: Context) : ProfilePrivateEvent()
    data class FirstNameChanged(val firstName: String) : ProfilePrivateEvent()
    data class LastNameChanged(val lastName: String) : ProfilePrivateEvent()
    data class AddressChanged(val address: String) : ProfilePrivateEvent()
    data class BusinessNameChanged(val businessName: String) : ProfilePrivateEvent()
    data class OpenFromChanged(val openFrom: String) : ProfilePrivateEvent()
    data class OpenTillChanged(val openTill: String) : ProfilePrivateEvent()
    data class OpenFromDaysChanged(val openFromDays: String) : ProfilePrivateEvent()
    data class OpenTillDaysChanged(val openTillDays: String) : ProfilePrivateEvent()
    data class AvailableAtChanged(val availableAt: String) : ProfilePrivateEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : ProfilePrivateEvent()

    data object SubmitEdit : ProfilePrivateEvent()
}