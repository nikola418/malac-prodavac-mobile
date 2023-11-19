package com.triforce.malacprodavac.presentation.add_edit_product.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.presentation.add_edit_product.AddEditProductEvent
import com.triforce.malacprodavac.presentation.registration.RegistrationFormEvent
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTextField(
    label: String,
    text: String,
    onTextValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    colorBackground: Color,
    colorForeground: Color

) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.h6,
            color = MP_Pink_Dark,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
        )
        OutlinedTextField(
            value = text,
            onValueChange = { value -> onTextValueChange(value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = MP_Black
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MP_Pink,
                containerColor = MP_White,
                focusedIndicatorColor = MP_Pink_Dark
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }
}