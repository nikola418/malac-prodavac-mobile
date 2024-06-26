package com.triforce.malacprodavac.presentation.store

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.model.Category
import com.triforce.malacprodavac.domain.repository.CategoriesRepository
import com.triforce.malacprodavac.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val repository: CategoriesRepository
) : ViewModel() {

    var state by mutableStateOf(StoreState())

    init {
        getCategories()
    }

    private fun getCategories() {

        state.copy(isLoading = true)

        viewModelScope.launch {
            repository.getCategories(true).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data is List<Category>) {
                            println(result.data)
                            state = state.copy(categories = result.data)
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
            }
        }
    }
}