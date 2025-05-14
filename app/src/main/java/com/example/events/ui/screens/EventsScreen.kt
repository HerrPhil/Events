package com.example.events.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventsScreen(
    items: SnapshotStateList<String>,
    newItemText: String,
    onValueChanged: (String) -> Unit,
    onReset: () -> Unit,
    onAddItem: (String) -> Unit,
    onDeleteItem: (Int) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.padding(top = 64.dp))

        // New Event Input
        OutlinedTextField(
            value = newItemText,
            onValueChange = onValueChanged,
            label = {
                Text(text = "New Item")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Add Event Action
        Button(
            onClick = {
                val validationPassed = {
                    !items.contains(newItemText) && newItemText.isNotBlank()
                }
                if (validationPassed()) {
                    onAddItem(newItemText)
                    onReset()
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add Item",
            )
            Text(text = "Add Item")
        }

        // List of Event Items
        val unselected = MaterialTheme.colorScheme.primaryContainer
        val selected = MaterialTheme.colorScheme.inversePrimary
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(items) { index, item ->

                // row color
                var color by remember { mutableStateOf(unselected) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Handle item click if needed
                            color = if (color == unselected) selected else unselected
                        }
                        .background(color),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item)
                    IconButton(
                        onClick = {
                            onDeleteItem(index)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Item"
                        )
                    }
                }
                HorizontalDivider()
            }
        }

    }

}
