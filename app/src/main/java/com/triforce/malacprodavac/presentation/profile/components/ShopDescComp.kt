package com.triforce.malacprodavac.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.ui.theme.MP_Black

@Composable
fun ShopDescComp(
    user: User?,
    shopUser: Shop? = null
) {
    val shop = shopUser ?: user?.shop

    if (user != null) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp
                )
        ) {
            Text(
                text = shop?.businessName ?: "Nepoznato ime prodavnice",
                style = MaterialTheme.typography.h5,
                color = MP_Black,
                fontWeight = FontWeight.W600
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = "Radno vreme: ".plus(shop?.openFrom ?: "Nepoznato").plus("-")
                    .plus(shop?.openTill ?: "Nepoznato"),
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                fontWeight = FontWeight.W300,
                maxLines = 1
            )
            Text(
                text = "Radni dani: ".plus(shop?.openFromDays ?: "Nepoznato").plus("-")
                    .plus(shop?.openTillDays ?: "Nepoznato"),
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                fontWeight = FontWeight.W300,
                maxLines = 1
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = "Kontakt: ".plus(user.phoneNumber ?: "Nepoznato"),
                style = MaterialTheme.typography.body1,
                color = MP_Black,
                fontWeight = FontWeight.W400,
                maxLines = 1
            )
        }
    }
}