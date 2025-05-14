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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun EventssssScreen(
    eventsUiState: EventsUiState,
    newItemTextUiState: String,
    onValueChanged: (String) -> Unit,
    validationPassed: () -> Boolean,
    onAddItem: () -> Unit,
    onResetNewItemText: () -> Unit,
    onDeleteItem: (Long) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.padding(top = 64.dp))

        // New Event Input
        OutlinedTextField(
            value = newItemTextUiState,
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
                if (validationPassed()) {
                    onAddItem()
                    onResetNewItemText()
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

        // Selection colors of rows
        val unselected = MaterialTheme.colorScheme.primaryContainer
        val selected = MaterialTheme.colorScheme.inversePrimary

        // List of Event Items
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = eventsUiState.events.sortedByDescending { it.timestamp },
                key = { event -> event.timestamp }
            ) { event ->

                // row color
                var isSelected by rememberSaveable { mutableStateOf(false) }
                val color = if (isSelected) selected else unselected

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Handle item click if needed
                            // toggle
                            isSelected = !isSelected
                        }
                        .background(color),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = event.name)
                    val instant = Instant.fromEpochMilliseconds(event.timestamp)
                    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                    val formattedDateTime = localDateTime.toString()
                    Text(text = formattedDateTime)
                    IconButton(
                        onClick = {
                            onDeleteItem(event.timestamp)
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
