package com.triforce.malacprodavac.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Orange
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun ProfileHeroComp(
    user: User?
){
    if ( user != null ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .height(160.dp)
            ) {

                Column {
                    Text(
                        text = "Malac ${user.roles.first()}",
                        style = MaterialTheme.typography.body1,
                        color = MP_White,
                        fontWeight = FontWeight.W500
                    )
                    Text(
                        text = "${user.firstName} ${user.lastName[0]}.",
                        style = MaterialTheme.typography.h4,
                        color = MP_White,
                        fontWeight = FontWeight.Black
                    )
                }

                Column {
                    Text(
                        text = "Jagodina, Srbija",
                        style = MaterialTheme.typography.body2,
                        color = MP_White,
                        fontWeight = FontWeight.W500,
                    )
                    Text(
                        text = "Ocena 9.8",
                        style = MaterialTheme.typography.body2,
                        color = MP_White,
                        fontWeight = FontWeight.W500
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(75.dp)
                ){
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "Izmeni",
                        tint = MP_White,
                        modifier = Modifier
                            .size(35.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "Izmeni",
                        tint = MP_White,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }

            }

            Image(
                painter = painterResource(androidx.customview.R.drawable.notify_panel_notification_icon_bg),
                contentDescription = "Round Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(175.dp)
                    .clip(CircleShape)
                    .border(3.dp, MP_White, CircleShape)
            )
        }
    }
}