package com.triforce.malacprodavac.presentation.store.category

import android.util.Log
import androidx.compose.material.Colors
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.model.Category
import com.triforce.malacprodavac.domain.model.Product
import com.triforce.malacprodavac.domain.repository.ProductRepository
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenLight
import com.triforce.malacprodavac.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(

    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle

): ViewModel() {

    var state by mutableStateOf(CategoryState())

    private val _categoryTitle = mutableStateOf(CategoryState(

        title = "Category title..."

    ))
    val categoryTitle: State<CategoryState> = _categoryTitle

    private val _categoryColor1 = mutableStateOf(MP_Green)
    val categoryColor1: State<Color> = _categoryColor1

    private val _categoryColor2 = mutableStateOf(MP_GreenLight)
    val categoryColor2: State<Color> = _categoryColor2

    init {
        savedStateHandle.get<Int>("categoryId")?.let { categoryId ->
            getProducts(true, categoryId);
        }
        savedStateHandle.get<String>("title")?.let { title ->
            _categoryTitle.value = _categoryTitle.value.copy(
                title = title
            )
        }
    }

    private fun getProducts(fetchFromRemote: Boolean, categoryId: Int) {

        viewModelScope.launch {

            repository.getProducts(categoryId, fetchFromRemote).collect({ result ->

                when (result) {

                    is Resource.Success -> {

                        if (result.data is List<Product>) {

                            println(result.data)
                            state = state.copy(products = result.data)

                        }

                    }

                    is Resource.Error -> {
                        Unit
                    }

                    is Resource.Loading -> {

                        state = state.copy(
                            isLoading = result.isLoading
                        )

                    }

                }

            })

        }

    }

}