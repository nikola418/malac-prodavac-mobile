package com.triforce.malacprodavac.presentation.editProduct

import android.content.Context
import android.net.Uri
import com.triforce.malacprodavac.domain.util.enum.Currency
import com.triforce.malacprodavac.domain.util.enum.UnitOfMeasurement

sealed class EditProductEvent {
    data class ChangeProductImages(val imageUris: List<Uri>) :
        EditProductEvent()

    data class UnitOfMeasurementChanged(val unitOfMeasurement: UnitOfMeasurement) :
        EditProductEvent()

    data class CurrencyChanged(val currency: Currency) : EditProductEvent()
    data class TitleChanged(val title: String) : EditProductEvent()
    data class DescChanged(val desc: String) : EditProductEvent()
    data class PriceChanged(val price: Double) : EditProductEvent()
    data class CategoryIdChanged(val categoryId: Int) : EditProductEvent()
    data class Submit(val context: Context) : EditProductEvent()
}