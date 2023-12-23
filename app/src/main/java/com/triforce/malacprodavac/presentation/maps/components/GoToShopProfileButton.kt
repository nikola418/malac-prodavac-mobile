package com.triforce.malacprodavac.presentation.maps.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun GoToShopProfileButton(
    navController: NavController,
    shop: Shop,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                spotColor = MP_Black,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(MP_White)
            .padding(
                vertical = 10.dp
            )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_green),
            contentDescription = "Shop",
            tint = MP_Green,
            modifier = Modifier
                .size(50.dp)
        )
        Text(
            text = "Poseti profil",
            style = MaterialTheme.typography.h5,
            color = MP_White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.PublicProfile.route + "?id=${shop.id}&role=1")
                }
                .clip(RoundedCornerShape(20.dp))
                .background(MP_Green)
                .width(width = 250.dp)
                .padding(vertical = 10.dp)
        )
    }
}