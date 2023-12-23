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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTextField(
    label: String? = null,
    text: String,
    isError: Boolean = false,
    onTextValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        if (label != null)
            Text(
                text = label,
                style = MaterialTheme.typography.body1,
                color = MP_Black,
                fontWeight = FontWeight.W400,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
            )
        OutlinedTextField(
            textStyle = MaterialTheme.typography.body2,
            value = text,
            isError = isError,
            onValueChange = { value -> onTextValueChange(value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            maxLines = 2,
            placeholder = {
                Text(
                    text = placeholder,
                    color = MP_Black
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MP_Black,
                containerColor = MP_White,
                focusedIndicatorColor = MP_Pink_Dark
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }
}