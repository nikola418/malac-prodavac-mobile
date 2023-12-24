package com.triforce.malacprodavac.presentation.notifications.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.domain.model.notifications.Notification
import com.triforce.malacprodavac.presentation.notifications.NotificationsViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun NotificationsItem(
    notification: Notification,
    viewModel: NotificationsViewModel
) {
    var showTime by remember { mutableStateOf(false) }

    var createdAt: String = notification.createdAt
    var dateOfCreating: String = createdAt.split("T")[0]
    var day = dateOfCreating.split("-")[2]
    var month = dateOfCreating.split("-")[1]
    var year = dateOfCreating.split("-")[0]
    var time: String = createdAt.split("T")[1]
    time = time.split(".")[0]
    dateOfCreating = day + "." + month + "." + year

    BoxWithConstraints(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = MP_Black,
                shape = RoundedCornerShape(3.5.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(MP_White)
            .fillMaxWidth()
            .clickable {
                showTime = !showTime
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = notification.notificationPayload.payload.data.title,
                style = MaterialTheme.typography.body1,
                color = MP_Black
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.logo_green),
                    contentDescription = "LogoGreen",
                    tint = MP_Pink,
                    modifier = Modifier
                        .size(26.dp)
                )
                Text(
                    text = "Datum: " + dateOfCreating + " Vreme: " + time,
                    style = MaterialTheme.typography.body2,
                    color = MP_Pink,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}