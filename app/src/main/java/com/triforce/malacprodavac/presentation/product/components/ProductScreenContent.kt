package com.triforce.malacprodavac.presentation.product.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.LinearGradient
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.components.RatingStars
import com.triforce.malacprodavac.presentation.components.RoundedBackgroundComp
import com.triforce.malacprodavac.presentation.components.ShowHighlightSectionComp
import com.triforce.malacprodavac.presentation.product.ProductEvent
import com.triforce.malacprodavac.presentation.product.ProductViewModel
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.ProductOptions
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Gray
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenLight
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun ProductScreenContent(
    navController: NavController,
    viewModel: ProductViewModel,
    padding: PaddingValues
) {
    var isCreateReviewOpen by remember { mutableStateOf(false) }
    var isCreateReplyReviewOpen by remember { mutableStateOf(false) }
    var reviewId by remember { mutableStateOf(0) }
    var clickedReviewId by remember { mutableStateOf(0) }

    val openCreateReviewDialog = { isCreateReviewOpen = true }
    val closeCreateReviewDialog = { isCreateReviewOpen = false }
    val openCreateReplyReviewDialog = { isCreateReplyReviewOpen = true }
    val closeCreateReplyReviewDialog = { isCreateReplyReviewOpen = false}

    val createReviewCallback = { text: String, rating: Int ->
        viewModel.onEvent(ProductEvent.CreateReview(text, rating))
    }

    var createReplyReviewCallback: (String, Int) -> Unit

    val state = viewModel.state
    val product = state.product
    val shop = state.shop
    val user = state.user

    val colorForeground = MP_Green
    val colorBackground = MP_GreenLight
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    if (state.createReviewError != null) {
        Toast
            .makeText(
                context,
                state.createReviewError,
                Toast.LENGTH_LONG
            )
            .show()
    }

    if (product != null && shop != null) {
        Box(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .background(MP_White)
                .padding(padding)
                .height(1400.dp)
        ) {

            if (isCreateReviewOpen) {
                CreateReviewDialog(closeCreateReviewDialog, createReviewCallback)
            }

            LinearGradient(color1 = colorForeground, color2 = colorBackground)
            RoundedBackgroundComp(top = 260.dp, color = MP_White)

            Column {
                Spacer(modifier = Modifier.padding(16.dp))
                ProductHeroImage(imageUrl = state.thumbnailUrl, imageKey = state.thumbnailKey)

                Spacer(modifier = Modifier.padding(22.dp))
                ProductDetails(product = product)

                if (product.shopId == viewModel.state.user?.shop?.id)
                    ProductOptions(product, navController, true)

                Spacer(Modifier.height(22.dp))
                ShowHighlightSectionComp(
                    navController = navController,
                    products = shop.products,
                    title = "Više od ${shop.businessName}",
                    route = Screen.PublicProfile.route + "?id=${shop.id}&role=1"
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Komentari:",
                        style = MaterialTheme.typography.body2,
                        color = MP_Black,
                        fontWeight = FontWeight.W500
                    )
                    IconButton(onClick = openCreateReviewDialog) {
                        Icon(Icons.Filled.Add, contentDescription = "Create a Review")
                    }
                }

                if (state.reviews != null) {

                    Spacer(Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        items(state.reviews) { review ->
                            reviewId = review.id

                            createReplyReviewCallback = { text: String, reviewId: Int->
                                viewModel.onEvent(ProductEvent.CreateReplyReview(text, reviewId))
                            }
                            if (isCreateReplyReviewOpen) {
                                CreateReplyReviewDialog(
                                    closeCreateReplyReviewDialog,
                                    createReplyReviewCallback,
                                    reviewId
                                )
                            }

                            Column {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = review.text.ifEmpty { "Korisnik nije ostavio komentar" },
                                        softWrap = true,
                                    )
                                    if (user?.roles!!.contains("Shop")) {
                                        IconButton(onClick = openCreateReplyReviewDialog) {
                                            Icon(
                                                Icons.Filled.Replay,
                                                contentDescription = "Create Reply a Review"
                                            )
                                        }
                                    }
                                }
                                RatingStars(
                                    rating = review.rating.toDouble()
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        review.createdAt.split("T")[0]
                                    )
                                    Text(
                                        review.customer?.user?.firstName + " " + review.customer?.user?.lastName
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 25.dp, top = 10.dp, bottom = 10.dp)
                                        .background(MP_Gray, shape = RoundedCornerShape(15.dp))
                                ) {
                                    if (state.replyReviews != null) {
                                        for (replyReview in state.replyReviews) {
                                            Row(
                                                modifier = Modifier.padding(start = 25.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.ArrowRight,
                                                    contentDescription = "Create Reply a Review"
                                                )
                                                Text(
                                                    text = replyReview.text.ifEmpty { "Korisnik nije odgovorio na komentar" },
                                                    softWrap = true,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}