package com.triforce.malacprodavac.presentation.myProducts

sealed class MyProductsEvent {
    data class OrderBy(val order: Int) : MyProductsEvent()
}