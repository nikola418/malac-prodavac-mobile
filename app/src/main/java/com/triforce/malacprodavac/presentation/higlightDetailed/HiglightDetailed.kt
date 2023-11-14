package com.triforce.malacprodavac.presentation.higlightDetailed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.LinearGradient
import com.triforce.malacprodavac.domain.model.Product
import com.triforce.malacprodavac.presentation.category.ShowcaseProducts
import com.triforce.malacprodavac.presentation.components.RoundedBackgroundComp
import com.triforce.malacprodavac.presentation.components.TitleDescComp
import com.triforce.malacprodavac.presentation.store.components.FilterSortComp
import com.triforce.malacprodavac.presentation.store.components.GoBackComp
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun HighlightDetailed(navController: NavController) {
    Box(
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
    ) {
        LinearGradient(color1 = MP_Pink, color2 = MP_Pink_Dark)

        RoundedBackgroundComp(top = 65.dp, color = MP_White)

        Column {
            GoBackComp("Više od prodavca", navController)
            TitleDescComp(
                title = "Sveži sokovi za Vaš užitak!",
                description = "Domaćinstvo Perun se generacijama bavi domaćom proizvodnjom sokova i sirupa od različitog voća koje se hladno cedi, zatim...",
                colorTitle = MP_Black,
                colorDesc = Color.DarkGray
            )
            FilterSortComp(navController)
            ShowcaseProducts(
                products = listOf(
                ), navController
            )
        }
    }
}