package com.triforce.malacprodavac.presentation.store.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun GoBackComp(
    msg: String,
    navController: NavController,
    modifier: Modifier,
    isLight: Boolean = false,
) {
    var color = MP_White
    if (isLight) color = Color.Gray

    Box {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(if (isLight) MP_White else Color.Transparent)
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .size(width = 240.dp, height = 35.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Search",
                    tint = color,
                    modifier = modifier
                        .size(25.dp)
                )

                Text(
                    text = if (msg.length > 15) {
                        msg.subSequence(0, 15).toString() + "..."
                    } else {
                        msg
                    },
                    style = MaterialTheme.typography.body1,
                    color = color,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_circle_notifications_24),
                contentDescription = "Notifications",
                tint = color,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        navController.navigate(Screen.NotificationScreen.route)
                    }
            )
        }
    }

}