package com.triforce.malacprodavac.presentation.add_edit_product.editProduct

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.domain.model.products.UpdateProductDto
import com.triforce.malacprodavac.domain.use_case.category.CategoryUseCase
import com.triforce.malacprodavac.domain.use_case.product.ProductUseCase
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.domain.util.compressedFileFromUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val categoryUseCases: CategoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(EditProductState())

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            if (productId != -1) {
                getProduct(productId)
            }
        }
        getCategories()
    }

    fun onEvent(event: EditProductEvent) {
        when (event) {
            is EditProductEvent.CategoryIdChanged -> state =
                state.copy(product = state.product?.copy(categoryId = event.categoryId))

            is EditProductEvent.CurrencyChanged -> state =
                state.copy(product = state.product?.copy(currency = event.currency))

            is EditProductEvent.DescChanged -> state =
                state.copy(product = state.product?.copy(desc = event.desc))

            is EditProductEvent.PriceChanged -> state =
                state.copy(product = state.product?.copy(price = event.price))

            is EditProductEvent.TitleChanged -> state =
                state.copy(product = state.product?.copy(title = event.title))

            is EditProductEvent.UnitOfMeasurementChanged -> state =
                state.copy(product = state.product?.copy(unitOfMeasurement = event.unitOfMeasurement))

            is EditProductEvent.Submit -> onSubmit(event.context)
            is EditProductEvent.ChangeProductImage -> state =
                state.copy(imageUri = event.imageUri, thumbUrl = event.imageUri.toString())

            is EditProductEvent.ToggleAvailable -> onToggleAvailable(event.product)
        }
    }

    private fun onToggleAvailable(product: Product) {
        val updatedProduct = product.copy(available = !product.available)
        state = state.copy(product = updatedProduct)

        val updateProduct = updateProductDtoFromState()
        updateProduct(updatedProduct.id, updateProduct)
    }

    private fun onSubmit(context: Context) {
        state = if (state.product?.title.isNullOrBlank())
            state.copy(titleError = "Unesite naziv!")
        else
            state.copy(titleError = null)

        state = if (state.product?.price!! <= 0.0)
            state.copy(priceError = "Unesite cenu!")
        else
            state.copy(priceError = null)

        if (state.titleError != null || state.priceError != null)
            return

        val updateProduct = updateProductDtoFromState()

        if (state.imageUri != null)
            changeProductImage(
                context,
                state.imageUri!!
            )

        updateProduct(state.product?.id!!, updateProduct)
    }

    private fun updateProductDtoFromState(): UpdateProductDto {
        return UpdateProductDto(
            unitOfMeasurement = state.product?.unitOfMeasurement,
            currency = state.product?.currency,
            title = state.product?.title,
            desc = state.product?.desc,
            price = state.product?.price,
            categoryId = state.product?.categoryId,
            availableAt = state.product?.availableAt,
            availableAtLatitude = state.product?.availableAtLatitude,
            availableAtLongitude = state.product?.availableAtLongitude,
            availableFromHours = state.product?.availableFromHours,
            availableTillHours = state.product?.availableTillHours,
            available = state.product?.available
        )
    }

    private fun changeProductImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            val file = compressedFileFromUri(context, uri)
            productUseCase.setProductImage(state.product?.id!!, file).collect {
                when (it) {
                    is Resource.Success -> getProduct(state.product?.id!!)
                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(it.isLoading)
                }
            }
        }
    }

    private fun updateProduct(
        productId: Int,
        updateProduct: UpdateProductDto
    ) {
        viewModelScope.launch {
            productUseCase.updateProduct(productId, updateProduct).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(product = it, isUpdateSuccessful = true)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun getProduct(productId: Int) {
        viewModelScope.launch {
            productUseCase.getProductForId(productId, true).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(
                                product = result.data,
                                thumbUrl = if (result.data.productMedia != null)
                                    "http://softeng.pmf.kg.ac.rs:10010/products/${result.data.productMedia.productId}/medias/${result.data.productMedia.id}"
                                else null
                            )
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryUseCases.getCategories().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(categories = it)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }


    private fun handleError() {
    }

    private fun handleLoading(isLoading: Boolean) {
        state = state.copy(isLoading = isLoading)
    }
}