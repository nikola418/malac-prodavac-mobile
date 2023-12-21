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

    var openFromDay: String? = null
    var openTillDay: String? = null

    if(shop?.openFromDays == "Monday")
        openFromDay = "Ponedeljak"
    else if(shop?.openFromDays == "Tuesday")
        openFromDay = "Utorak"
    else if(shop?.openFromDays == "Wednesday")
        openFromDay = "Sreda"
    else if(shop?.openFromDays == "Thursday")
        openFromDay = "Četvrtak"
    else if(shop?.openFromDays == "Friday")
        openFromDay = "Petak"
    else if(shop?.openFromDays == "Saturday")
        openFromDay = "Subota"
    else if(shop?.openFromDays == "Sunday")
        openFromDay = "Nedelja"

    if(shop?.openTillDays == "Monday")
        openTillDay = "Ponedeljak"
    else if(shop?.openTillDays == "Tuesday")
        openTillDay = "Utorak"
    else if(shop?.openTillDays == "Wednesday")
        openTillDay = "Sreda"
    else if(shop?.openTillDays == "Thursday")
        openTillDay = "Četvrtak"
    else if(shop?.openTillDays == "Friday")
        openTillDay = "Petak"
    else if(shop?.openTillDays == "Saturday")
        openTillDay = "Subota"
    else if(shop?.openTillDays == "Sunday")
        openTillDay = "Nedelja"

    if (user != null) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 8.dp,
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
                text = "Radni dani: ".plus(openFromDay ?: "Nepoznato").plus("-")
                    .plus(openTillDay ?: "Nepoznato"),
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