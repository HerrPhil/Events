package com.example.events.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.events.ui.screens.EventsViewModel
import com.example.events.ui.screens.EventssssScreen

@Composable
fun EventsApp() {

    // Here is google chat gpt suggestion - it covers the UI parts really well
    // I need to work in the coroutine stuff, sorting stuff.
    // Learning: See documentation snippet.

//    val items by rememberSaveable {
//        //        Creating a MutableState object with a mutable collection type Toggle info (⌘F1)
//        //        Inspection info: Writes to mutable collections inside a MutableState
//        //        will not cause a recomposition - only writes to the MutableState itself will.
//        //        In most cases you should either use a read-only collection (such as List or Map)
//        //        and assign a new instance to the MutableState when your data changes,
//        //        or you can use an snapshot-backed collection such as SnapshotStateList
//        //        or SnapshotStateMap which will correctly cause a recomposition
//        //        when their contents are modified.
//        mutableStateOf(
//            mutableListOf("Event 1", "Event 2", "Event 3")
//        )
//    }

    // With respect to the documentation above, from the rememberSaveable fun decompile,
    // the mutableStateListOf() function will schedule recomposition.
    // To support that, a list Saver is needed to tell the SaveableStateRegistry that my list
    // is saveable.
//    val items: SnapshotStateList<String> = rememberSaveable(
//        saver = listSaver(
//            save = { it.toList() },
//            restore = { it.toMutableStateList() }
//        )
//    ) {
//        mutableStateListOf("Event 1", "Event 2", "Event 3")
//    }
//
//    var newItemText: String by rememberSaveable {
//        mutableStateOf("")
//    }


//    val onValueChanged: (String) -> Unit = { newItemText = it }
//    val onReset: () -> Unit = { newItemText = "" }
//    val onAddItem: (String) -> Unit = { items.add(it) }
//    val onDeleteItem: (Int) -> Unit = { items.removeAt(it) }
//
//    EventsScreen(items, newItemText, onValueChanged, onReset, onAddItem, onDeleteItem)


    val viewModel: EventsViewModel = viewModel()
    val eventsUiState = viewModel.uiEventsState.collectAsState().value
//    val newItemTextUiState = viewModel.uiNewItemTextState.collectAsState().value
    val newItemTextUiState = viewModel.uiNewItemTextState.value

    val onValueChangedUsingViewModel: (String) -> Unit = { viewModel.setNewItemText(it) }
    val onResetNewItemTextUsingViewModel: () -> Unit = { viewModel.resetNewItemText() }
    val onAddItemUsingViewModel: () -> Unit = { viewModel.addEvent() }
    val onDeleteItemUsingViewModel: (Long) -> Unit = { viewModel.deleteEvent(it) }
    val validationPassed: () -> Boolean = { viewModel.validationPassed() }

    EventssssScreen(
        eventsUiState,
        newItemTextUiState,
        onValueChangedUsingViewModel,
        validationPassed,
        onAddItemUsingViewModel,
        onResetNewItemTextUsingViewModel,
        onDeleteItemUsingViewModel
    )

}
