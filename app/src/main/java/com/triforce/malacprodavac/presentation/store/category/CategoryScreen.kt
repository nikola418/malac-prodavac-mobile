@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.triforce.malacprodavac.presentation.store.category

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.LinearGradient
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu
import com.triforce.malacprodavac.presentation.components.RoundedBackgroundComp
import com.triforce.malacprodavac.presentation.components.ShowHighlightedProducts
import com.triforce.malacprodavac.presentation.highlightSection.components.SortAndFilterCategoryProducts
import com.triforce.malacprodavac.presentation.store.components.CategorySectionHeader
import com.triforce.malacprodavac.presentation.store.components.GoBackComp
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_Orange
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StoreCategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    val products: List<Product> = state.products

    val titleState = viewModel.categoryTitle.value

    var colorBackground = MP_White
    var colorForeground = MP_White

    if (viewModel.currentCategoryId != null) {
        if (viewModel.currentCategoryId!! % 3 == 1) {
            colorBackground = MP_GreenDark
            colorForeground = MP_Green
        } else if (viewModel.currentCategoryId!! % 3 == 2) {
            colorBackground = MP_Pink_Dark
            colorForeground = MP_Pink
        } else {
            colorBackground = MP_Orange_Dark
            colorForeground = MP_Orange
        }
    }

    Box(
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
    ) {
        LinearGradient(color1 = colorBackground, color2 = colorForeground)

        RoundedBackgroundComp(top = 250.dp, color = MP_White)

        Column {
            GoBackComp("Malac Pijaca", navController)
            CategorySectionHeader(
                titleState.title,
                "Podržite zajednicu, podržavajte lokalno preduzetništvo. Vaša podrška čini razliku!",
                colorBackground
            )

            Spacer(modifier = Modifier.size(21.dp))
            SortAndFilterCategoryProducts(navController, viewModel)
            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 15.dp),
                placeholder = {
                    Text(
                        text = "Pretražite...",
                        color = colorBackground,
                        style = MaterialTheme.typography.body2,
                    )
                },
                trailingIcon = {
                    Icon(Icons.Filled.Search, "", tint = colorBackground)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorBackground,
                    containerColor = MP_White,
                    focusedIndicatorColor = colorBackground
                ),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                textStyle = MaterialTheme.typography.body2
            )

            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                ShowHighlightedProducts(
                    isLoading = state.isLoading,
                    isLastPage = state.isLastPage,
                    loadNextPage = {
                        viewModel.loadNextPage()
                    },
                    products = products,
                    navController = navController,
                    bottomNavigation = true
                )
            }
        }
        BottomNavigationMenu(
            navController = navController,
            items = listOf(
                BottomNavigationMenuContent(
                    title = "Početna",
                    graphicID = Icons.Default.Home,
                    screen = Screen.HomeScreen,
                    isActive = false
                ),
                BottomNavigationMenuContent(
                    title = "Market",
                    graphicID = ImageVector.vectorResource(R.drawable.logo_green),
                    screen = Screen.StoreScreen,
                    isActive = true
                ),
                BottomNavigationMenuContent(
                    title = "Profil",
                    graphicID = Icons.Default.Person,
                    screen = Screen.PrivateProfile,
                    isActive = false
                ),
                BottomNavigationMenuContent(
                    title = "Korpa",
                    graphicID = Icons.Default.ShoppingCart,
                    screen = Screen.CartScreen,
                    isActive = false
                )
            ), modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}