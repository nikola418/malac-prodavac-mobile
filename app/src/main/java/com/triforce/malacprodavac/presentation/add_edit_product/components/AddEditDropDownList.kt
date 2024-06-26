package com.triforce.malacprodavac.presentation.add_edit_product.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_White


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDropDownList(
    entries: List<Any> = emptyList(),
    selectedEntry: String? = null,
    handleSelect: (Any) -> Unit,
    label: String,
    fill: Boolean
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selected by if (selectedEntry != null)
        remember { mutableStateOf(selectedEntry) }
    else mutableStateOf("")


    Column(
        modifier = if (fill) {
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp))
        } else {
            Modifier.width(150.dp)
        }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            color = MP_Black,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
        )
        // menu box
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
            isExpanded = it
        }) {
            TextField(
                value = selected.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clip(RoundedCornerShape(10.dp)), // menuAnchor modifier must be passed to the text field for correctness.
                readOnly = true,
                onValueChange = { },
                trailingIcon = { TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            // menu
            ExposedDropdownMenu(
                expanded = isExpanded, onDismissRequest = {
                    isExpanded = false
                }, modifier = Modifier
                    .width(150.dp)
                    .background(MP_White)
            ) {
                entries.forEach { entry ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                entry.toString(), color = MP_Black
                            )
                        },
                        onClick = {
                            selected = entry.toString()
                            isExpanded = false
                            handleSelect(entry)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}