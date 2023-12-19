package com.triforce.malacprodavac.presentation.profile.profilePrivate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Pink

@Composable
fun CourierDescComp(
    user: User?
) {
    if (user != null) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Kontakt:",
                        style = MaterialTheme.typography.body1,
                        color = MP_Black,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = user.phoneNumber ?: "Nepoznato",
                        style = MaterialTheme.typography.h5,
                        color = MP_Pink,
                        fontWeight = FontWeight.W500
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = "Adresa:",
                        style = MaterialTheme.typography.body1,
                        color = MP_Black,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = user.address ?: "Nepoznato",
                        style = MaterialTheme.typography.h5,
                        color = MP_Pink,
                        fontWeight = FontWeight.W500
                    )
                }
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.logo_green),
                    contentDescription = "Malac Prodavac",
                    tint = MP_Pink,
                    modifier = Modifier
                        .size(130.dp)
                )
            }
        }
    }
}