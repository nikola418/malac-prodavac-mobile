package com.triforce.malacprodavac.presentation.store.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.LinearGradient
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun StoreCategoryScreen(navController: NavController)
{
    Box(
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
    ){
        LinearGradient(color1 = MP_Pink, color2 = MP_Pink_Dark )

        Surface (
            color = MP_White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1F)
                .padding(top = 150.dp)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
        ){

        }
    }
}