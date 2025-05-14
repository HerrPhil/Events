package com.example.events.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.events.model.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

    private val _uiEventsState = MutableStateFlow(EventsUiState())
    val uiEventsState: StateFlow<EventsUiState> = _uiEventsState

    private val _uiNewItemTextState = MutableStateFlow("")
    val uiNewItemTextState = _uiNewItemTextState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        viewModelScope.launch {
            delay(1)
            initializeNewItemText()
        }
    }

    private suspend fun initializeNewItemText() {
        _uiNewItemTextState.update {
            ""
        }
    }

    fun addEvent() {
        val newValue = _uiNewItemTextState.value
        val timestampedEvent = Event(newValue, System.currentTimeMillis())
        Log.i("Add Event", "Event: name of ${timestampedEvent.name} at ${timestampedEvent.timestamp}")
        _uiEventsState.update {
            it.copy(
                events = it.events.plus(timestampedEvent)
            )
        }
    }

    fun deleteEvent(timestamp: Long) {
        _uiEventsState.update {
            it.copy(
                events = it.events.filter { it.timestamp != timestamp }
            )
        }
    }

    fun setNewItemText(value: String) {
        _uiNewItemTextState.update {
            value
        }
    }

    fun resetNewItemText() {
        _uiNewItemTextState.update {
            ""
        }
    }

    fun validationPassed(): Boolean {
        val eventsList = _uiEventsState.value.events
        val newItemText = _uiNewItemTextState.value
        val containsNewItemText = eventsList.any { it.name == newItemText }
        val newItemTextIsNotBlank = newItemText.isNotBlank()
        return !containsNewItemText && newItemTextIsNotBlank
    }

}
