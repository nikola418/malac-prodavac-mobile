package com.triforce.malacprodavac.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateEvent
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateViewModel
import com.triforce.malacprodavac.ui.theme.MP_White


@Composable
fun GoBackCompLogout(
    msg: String,
    navController: NavController,
    viewModel: ProfilePrivateViewModel
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
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
                tint = MP_White,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        //navController.popBackStack()
                        navController.navigate(Screen.HomeScreen.route)
                    }
            )

            Text(
                text = msg,
                style = MaterialTheme.typography.body1,
                color = MP_White,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }


        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "logout",
            tint = MP_White,
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    viewModel
                        .onEvent(ProfilePrivateEvent.Logout)
                }
        )
    }
}