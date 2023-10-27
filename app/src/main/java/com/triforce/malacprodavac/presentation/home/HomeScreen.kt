package com.triforce.malacprodavac.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import com.triforce.malacprodavac.Screen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.Feature
import com.triforce.malacprodavac.presentation.RegistrationFormEvent
import com.triforce.malacprodavac.presentation.login.LoginFormEvent
import com.triforce.malacprodavac.presentation.login.LoginViewModel
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_GreenLight
import com.triforce.malacprodavac.ui.theme.MP_Orange
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
    ) {
        Column {
            GreetingSection()
            CategoriesSection(categories = listOf("Malac Pijaca", "Profil", "Porudžbine"))
            GoToStoreSection()
            RecommendedFeaturesSection(
                features = listOf(
                    Feature(
                        title = "Prodavnica",
                        graphicID = Icons.Default.AddCircle,
                        backgroundColor = MP_GreenDark
                    ),
                    Feature(
                        title = "Moj Profil",
                        graphicID = Icons.Default.AccountCircle,
                        backgroundColor = MP_Orange
                    ),
                    Feature(
                        title = "Omiljeno",
                        graphicID = Icons.Default.Favorite,
                        backgroundColor = MP_GreenLight
                    ),
                    Feature(
                        title = "Korpa",
                        graphicID = Icons.Default.ShoppingCart,
                        backgroundColor = MP_Green
                    )
                )
            )
        }
    }
}

@Composable
fun GreetingSection(
    name: String = "Filip"
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Dobrodošao $name!",
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "Želimo Vam srećnu kupovinu!",
                style = MaterialTheme.typography.body1
            )
        }

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = MP_White,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Composable
fun CategoriesSection(
    categories: List<String>
) {
    var selectedCategoryIndex by remember {
        mutableStateOf(0)
    }

    LazyRow {
        items(categories.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedCategoryIndex = it // current index of the box
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedCategoryIndex == it)
                            MP_GreenDark
                        else
                            MP_GreenLight
                    )
                    .padding(15.dp)
            )
            {
                Text(text = categories[it], color = MP_White)
            }
        }
    }
}

@Composable
fun GoToStoreSection(
    color: Color = MP_Pink
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Istraži našu prodavnicu",
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "Od sirupa do sira!",
                style = MaterialTheme.typography.body1,
                color = MP_White
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MP_GreenDark)
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Store",
                tint = MP_White,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

@Composable
fun RecommendedFeaturesSection(
    features: List<Feature>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Istaknute Akcije",
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .padding(15.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 7.5.dp,
                end = 7.5.dp,
                bottom = 100.dp
            ), // 100 dp bottom padding because navigation
            modifier = Modifier.fillMaxHeight(),
        ) {
            items(features.size) {// how many items do we have
                // define one of items
                RecommendedFeatureItem(feature = features[it])
            }
        }
    }
}

@Composable
fun RecommendedFeatureItem(
    feature: Feature
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1F) // ratio is 1x1 so whatever the width is, the hegiht will be the same
            .clip(RoundedCornerShape(10.dp))
            .background(feature.backgroundColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.h2,
                lineHeight = 26.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            Icon(
                imageVector = feature.graphicID,
                contentDescription = feature.title,
                tint = MP_White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
            Text(
                text = "Otvori",
                color = MP_White,
                fontSize = 14.sp,
                fontWeight =  FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        // we need to handle the click...
                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MP_Pink)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}