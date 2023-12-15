package com.triforce.malacprodavac.presentation.maps.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.presentation.components.ShowHighlightedProducts
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun BottomMapShopDetails(
    selectedShop: Shop?,
    showShopDetails: Boolean,
    navController: NavController
) {
    if (selectedShop != null && showShopDetails) {
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)

        LaunchedEffect(Unit) {
            sheetState.show()
        }

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.logo_green),
                            contentDescription = "Malac Prodavac",
                            tint = MP_Green,
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Text(
                            text = "Malac Prodavac",
                            style = MaterialTheme.typography.h4,
                            fontWeight = FontWeight.Bold,
                            color = MP_GreenDark,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))

                    ShopDetailItem("Naziv prodavca", selectedShop.businessName ?: "-")
                    Spacer(modifier = Modifier.height(16.dp))

                    ShopDetailItem("Otvoreno od", selectedShop.openFrom ?: "-")
                    ShopDetailItem("Zatvara se", selectedShop.openTill ?: "-")
                    Spacer(modifier = Modifier.height(16.dp))

                    ShopDetailItem("Danima od", selectedShop.openFromDays ?: "-")
                    ShopDetailItem("Do", selectedShop.openTillDays ?: "-")
                    Spacer(modifier = Modifier.height(16.dp))

                    /*ShopDetailItem("Lat", selectedShop.availableAtLatitude.toString())
                    ShopDetailItem("Long", selectedShop.availableAtLongitude.toString())
                    Spacer(modifier = Modifier.height(16.dp))*/

                    ShopHighlightProduct(
                        navController = navController,
                        products = selectedShop.products,
                        title = "Preporučeni proizvodi",
                        route = Screen.HighlightSection.route + "?id=${selectedShop.id}"
                    )

                    GoToShopProfileButton(navController = navController, shop = selectedShop)
                }
            }
        ) {
        }
    }
}

@Composable
fun ShopDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = MP_Green,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.W500,
            color = MP_Black
        )
    }
}

@Composable
fun ShopHighlightProduct(
    navController: NavController,
    products: List<Product>?,
    title: String,
    route: String
) {
    val subProducts = if (products != null) {
        products.subList(
            0, if (products.size <= 2) {
                products.size
            } else {
                2
            }
        )
    } else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            androidx.compose.material.Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MP_Black,
                fontWeight = FontWeight.W500
            )
            androidx.compose.material.Text(
                text = "Vidi više",
                style = MaterialTheme.typography.caption,
                color = MP_White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navController.navigate(route)
                    }
                    .clip(RoundedCornerShape(15.dp))
                    .background(MP_Pink)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }

        ShowHighlightedProducts(products = subProducts, navController = navController)
    }
}