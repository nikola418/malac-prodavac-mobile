package com.triforce.malacprodavac.presentation.highlightSection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.LinearGradient
import com.triforce.malacprodavac.presentation.components.RoundedBackgroundComp
import com.triforce.malacprodavac.presentation.components.ShowHighlightedProducts
import com.triforce.malacprodavac.presentation.components.SortAndFilter
import com.triforce.malacprodavac.presentation.components.TitleDescComp
import com.triforce.malacprodavac.presentation.store.components.GoBackComp
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighlightSection(
    navController: NavController,
    viewModel: HighlightSectionViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    val shop = state.shop
    val products = state.products

    Box(
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
    ) {
        LinearGradient(color1 = MP_Pink, color2 = MP_Pink_Dark)
        RoundedBackgroundComp(top = 65.dp, color = MP_White)

        Column {

            GoBackComp("Više od ${shop?.businessName}", navController)

            TitleDescComp(
                title = "${shop?.businessName} za Vaš užitak!",
                description = "Domaćinstvo ${shop?.businessName} Dostupni od ${shop?.openFromDays} do ${shop?.openTillDays} Dana!",
                colorTitle = MP_Black,
                colorDesc = Color.DarkGray
            )

            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 16.dp),
                placeholder = {
                    Text(
                        text = "Pretražite ${shop?.businessName}...",
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

            SortAndFilter(navController, viewModel)

            if (!state.isLoading) {
                if (isSearching) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    ShowHighlightedProducts(
                        products = products,
                        navController = navController,
                        bottomNavigation = true
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