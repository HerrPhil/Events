package com.example.events.ui.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.events.model.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

    // ViewModel: Use MutableStateFlow to maintain and expose state to multiple consumers,
    // especially when you need to use flow operators or when the state needs to be observed
    // in non-Compose environments.
    // Non-Compose: Use MutableStateFlow when you need to manage state in a way that is not tied to
    // Compose, such as in a repository or a service.
    private val _uiEventsState = MutableStateFlow(EventsUiState())
    val uiEventsState: StateFlow<EventsUiState> = _uiEventsState

    //    private val _uiNewItemTextState = MutableStateFlow("")
//    val uiNewItemTextState: StateFlow<String> = _uiNewItemTextState

    // Use mutableStateOf within Composables to manage state that is specific to the UI and needs
    // to trigger recompositions.
    var uiNewItemTextState: MutableState<String> = mutableStateOf("")
        private set

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
//        _uiNewItemTextState.update {
//            ""
//        }
        uiNewItemTextState.value = ""
    }

    fun addEvent() {
        val newValue = uiNewItemTextState.value
        val timestampedEvent = Event(newValue, System.currentTimeMillis())
        Log.i(
            "Add Event",
            "Event: name of ${timestampedEvent.name} at ${timestampedEvent.timestamp}"
        )
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
//        _uiNewItemTextState.update {
//            value
//        }
        uiNewItemTextState.value = value
    }

    fun resetNewItemText() {
//        _uiNewItemTextState.update {
//            ""
//        }
        uiNewItemTextState.value = ""
    }

    fun validationPassed(): Boolean {
        val eventsList = _uiEventsState.value.events
        val newItemText = uiNewItemTextState.value
        val containsNewItemText = eventsList.any { it.name == newItemText }
        val newItemTextIsNotBlank = newItemText.isNotBlank()
        return !containsNewItemText && newItemTextIsNotBlank
    }

}
