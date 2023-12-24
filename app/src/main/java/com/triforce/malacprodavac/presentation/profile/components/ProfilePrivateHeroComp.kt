package com.triforce.malacprodavac.presentation.profile.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateEvent
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_GreenLight
import com.triforce.malacprodavac.ui.theme.MP_Orange
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_White
import kotlinx.coroutines.Dispatchers

@Composable
fun ProfilePrivateHeroComp(
    user: User?,
    navController: NavController,
    viewModel: ProfilePrivateViewModel = hiltViewModel(),
    private: Boolean
) {
    val state = viewModel.state

    if (user != null) {
        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                    .background(
                        if (!private) {
                            Brush.linearGradient(

                                0.0f to MP_GreenDark, 500.0f to MP_GreenLight,

                                start = Offset.Zero, end = Offset.Infinite
                            )
                        } else {
                            Brush.linearGradient(

                                0.0f to MP_Orange_Dark, 500.0f to MP_Orange,

                                start = Offset.Zero, end = Offset.Infinite
                            )
                        }
                    )
            ) {
                GoBackCompLogout(
                    msg = "Profil", navController = navController, viewModel = viewModel
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 60.dp, bottom = 30.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.height(160.dp)
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
                                style = MaterialTheme.typography.h5,
                                color = MP_White,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Column {
                            Text(
                                text = "${user.address}",
                                style = MaterialTheme.typography.body2,
                                color = MP_White,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.width(150.dp)
                            )
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.body2,
                                color = MP_White,
                                fontWeight = FontWeight.W500
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (private) {
                                if (user.roles.first().equals("Shop", ignoreCase = true)) {
                                    Icon(imageVector = Icons.Rounded.AddCircle,
                                        contentDescription = "Dodaj",
                                        tint = MP_White,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clickable {
                                                navController.navigate(Screen.AddProduct.route)
                                            })
                                }
                                Icon(imageVector = Icons.Rounded.Edit,
                                    contentDescription = "Izmeni",
                                    tint = MP_White,
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .size(30.dp)
                                        .clickable { viewModel.onEvent(ProfilePrivateEvent.Edit) }
                                        .then(
                                            if (state.isEditing) Modifier.background(
                                                MP_Black,
                                                RoundedCornerShape(10.dp)
                                            ) else Modifier.background(
                                                Color.Unspecified
                                            )
                                        )
                                )
                            }
                        }
                    }

                    val launcher = LocalContext.current.let { it ->
                        rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.PickVisualMedia()
                        ) { uri ->
                            if (uri != null) {
                                viewModel.onEvent(
                                    ProfilePrivateEvent.ChangeProfilePicture(
                                        uri, it
                                    )
                                )
                                if (state.profileImageKey != null) {
                                    it.imageLoader.memoryCache?.remove(MemoryCache.Key(state.profileImageKey))
                                }
                            }
                        }
                    }

                    val placeholder = coil.base.R.drawable.notify_panel_notification_icon_bg
                    val imageRequest = ImageRequest.Builder(LocalContext.current)
                        .data(state.profileImageUrl)
                        .dispatcher(Dispatchers.IO)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .memoryCacheKey(state.profileImageKey)
                        .placeholder(placeholder)
                        .error(placeholder)
                        .fallback(placeholder)
                        .build()

                    if (private) {
                        AsyncImage(
                            model = imageRequest,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .border(3.dp, MP_White, CircleShape)
                                .clickable {
                                    launcher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}