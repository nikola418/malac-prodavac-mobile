package com.triforce.malacprodavac.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun BottomNavigationMenu(
    navController: NavController,
    items: List<BottomNavigationMenuContent>,
    modifier: Modifier = Modifier,
    selectedColor: Color = MP_Orange_Dark,
    selectedTextColor: Color = MP_Orange_Dark,
    nonActiveTextColor: Color = MP_Green,
    initSelectedItemID: Int = 0 // select first item by default
) {
    var selectedItemID by remember {
        mutableStateOf(initSelectedItemID)
    }

    Box(
        modifier
            .shadow(
                elevation = 16.dp,
                spotColor = MP_Black,
                ambientColor = MP_Green,
                shape = RectangleShape
            )
            .fillMaxWidth()
            .background(MP_White)
            .padding(vertical = 10.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationMenuItem(
                    navController = navController,
                    item = item,
                    isActive = index == selectedItemID,
                    selectedColor = selectedColor,
                    selectedTextColor = selectedTextColor,
                    nonActiveTextColor = nonActiveTextColor
                ) {
                    selectedItemID = index // updated
                }
            }
        }
    }

}
