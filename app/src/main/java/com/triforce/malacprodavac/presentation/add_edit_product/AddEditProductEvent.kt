package com.triforce.malacprodavac.presentation.add_edit_product

import android.content.Context
import android.net.Uri
import com.triforce.malacprodavac.util.enum.Currency
import com.triforce.malacprodavac.util.enum.UnitOfMeasurement

sealed class AddEditProductEvent {
    data class ChangeProductImages(val context: Context, val imageUris: List<Uri>) :
        AddEditProductEvent()

    data class UnitOfMeasurementChanged(val unitOfMeasurement: UnitOfMeasurement) :
        AddEditProductEvent()

    data class CurrencyChanged(val currency: Currency) : AddEditProductEvent()
    data class TitleChanged(val title: String) : AddEditProductEvent()
    data class DescChanged(val desc: String) : AddEditProductEvent()
    data class PriceChanged(val price: Double) : AddEditProductEvent()
    data class CategoryIdChanged(val categoryId: Int) : AddEditProductEvent()
    object Submit : AddEditProductEvent()
}