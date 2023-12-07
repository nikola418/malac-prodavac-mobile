package com.triforce.malacprodavac.presentation.product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.ui.theme.MP_White
import kotlinx.coroutines.Dispatchers

@Composable
fun ProductHeroImage(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(start = 15.dp, end = 15.dp)
            .background(MP_White, RoundedCornerShape(10.dp))
            .padding(35.dp)
    ) {
        val placeholder = R.drawable.logo_green
        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .dispatcher(Dispatchers.IO)
//            .memoryCachePolicy(CachePolicy.ENABLED)
//            .memoryCacheKey(state.profileImageKey)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .build()
        AsyncImage(
            model = imageRequest,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}