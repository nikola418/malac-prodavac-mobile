package com.triforce.malacprodavac.presentation.myTransactions.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_White
import kotlinx.coroutines.Dispatchers

@Composable
fun ProductImage(
    product: Product?,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    if (product != null) {

        val imageUrl =
            if (product.productMedia != null) "http://softeng.pmf.kg.ac.rs:10010/products/${product.productMedia.productId}/medias/${product.productMedia.id}" else null

        val placeholder = R.drawable.logo_green

        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .dispatcher(Dispatchers.IO)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCacheKey(imageUrl)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .build()


        Box(
            modifier = modifier
                .size(
                    width = width,
                    height = height
                )
                .shadow(
                    elevation = 5.dp,
                    spotColor = MP_Black,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(2.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MP_White)
                .clip(RoundedCornerShape(10.dp))
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = "ProductImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}