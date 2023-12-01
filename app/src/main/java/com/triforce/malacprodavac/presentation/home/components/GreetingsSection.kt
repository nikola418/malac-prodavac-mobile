package com.triforce.malacprodavac.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.ui.theme.MP_White
import androidx.compose.foundation.layout.Column as Column

@Composable
fun GreetingSection(
    name: String = "Filip",
    msg: String,
    subMsg: String = "",
    navController: NavController
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
                text = msg,
                style = MaterialTheme.typography.h4,
                color = MP_White
            )
            Text(
                text = subMsg,
                style = MaterialTheme.typography.body1,
                color = MP_White
            )
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_circle_notifications_24),
            contentDescription = "Notifications",
            tint = MP_White,
            modifier = Modifier
                .size(45.dp)
                .clickable {
                    navController.navigate(Screen.NotificationScreen.route)
                }
        )
    }
}
