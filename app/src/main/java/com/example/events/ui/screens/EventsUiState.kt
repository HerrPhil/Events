package com.example.events.ui.screens

import com.example.events.model.Event

data class EventsUiState(
    val events: List<Event> = listOf()
)
