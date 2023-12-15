package com.triforce.malacprodavac.presentation.myProducts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.presentation.components.ShowHighlightedProducts
import com.triforce.malacprodavac.presentation.myProducts.MyProductsViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProductsContentScreen(
    navController: NavController,
    viewModel: MyProductsViewModel,
    paddingValues: PaddingValues
) {
    val state = viewModel.state
    val shop = state.user?.shop

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    val spaceBetween = 6.dp

    if (shop != null)
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(MP_White)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.padding(spaceBetween))
                Text(
                    text = "Moji proizvodi",
                    style = MaterialTheme.typography.h6,
                    color = MP_Black,
                    fontWeight = FontWeight.W400
                )
                Spacer(modifier = Modifier.padding(spaceBetween))

                OutlinedTextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 16.dp),
                    placeholder = {
                        Text(
                            text = "Pretražite ${shop.businessName}...",
                            style = MaterialTheme.typography.body2,
                            color = MP_Black
                        )
                    },
                    trailingIcon = {
                        Icon(Icons.Filled.Search, "", tint = MP_Green)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MP_Green,
                        containerColor = MP_White,
                        focusedIndicatorColor = MP_Green
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    textStyle = MaterialTheme.typography.body2
                )
                MyProductsSort(viewModel)
                Spacer(modifier = Modifier.padding(spaceBetween))

                if (!state.isLoading) {
                    if (isSearching) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    } else {
                        ShowHighlightedProducts(
                            products = state.products,
                            navController,
                            bottomNavigation = false
                        )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
}