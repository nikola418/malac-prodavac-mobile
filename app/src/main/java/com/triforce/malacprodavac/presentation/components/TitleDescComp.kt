package com.triforce.malacprodavac.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.ui.theme.MP_Black

@Composable
fun TitleDescComp(
    title: String,
    description: String,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth(1F)
            .height(
                height = 130.dp
            )
            .padding(
                horizontal = 20.dp
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = MP_Black,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center
        )
        Text(
            text = description,
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
            fontWeight = FontWeight.W300,
            textAlign = TextAlign.Center
        )
    }
}