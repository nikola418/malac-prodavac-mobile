package com.triforce.malacprodavac.presentation.profile.profilePrivate.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownListWorkTime(
    entries: List<Any> = emptyList(),
    handleSelect: (Any) -> Unit,
    label: String
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedEntry by remember { mutableStateOf(entries.first().toString()) }

    // menu box
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedEntry,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // menuAnchor modifier must be passed to the text field for correctness.
            readOnly = true,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        // menu
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }, modifier = Modifier.fillMaxWidth()
        ) {
            entries.forEach { entry ->
                DropdownMenuItem(
                    text = {
                        Text(
                            entry.toString()
                        )
                    },
                    onClick = {
                        selectedEntry = entry.toString()
                        isExpanded = false
                        handleSelect(entry)
                    }, modifier = Modifier.fillMaxWidth(),
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}