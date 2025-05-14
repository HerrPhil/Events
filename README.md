# Events Project

Produce a simple app to track events.

## Technical Interview

I had a coding interview. The feature story was deceptively simple. The final product was to be a list of events.

The list of requirements is:

* show event list; simple String of event name is okay.
* have ability to add and delete
* demonstrate coroutine use
* store data in-memory
* sort in descending order of timestamp of list items
* validate input, like, do not allow blank strings
* remember state on (re)composition and configuration change
* complete it in 90 minutes

After the test, I timed the coding of my local solution with a count-up timer web page.

The exercise stipulated not to use chat GPT. That too bad. Fabricating UI code can be tedious with zero wireframes. The chat GPT response via the Brave browser produced the basis of a screen that was 80% of what was needed.

One shortfall of the generated code was that its in-memory choice never triggers recomposition. It offered a mutable state of a mutable list of data. Changes to the mutable list do not qualify as state change. The incremental re-factoring is to use a snapshot state list, from rememberSaveable(), of a mutable state list of data. In addition, rememberSaveable() needs a listSaver() result to know how transform data to saveable values for the save registry and to transform saveable values to state values when the mutable list is restored.

On the next re-factoring, when I added the view model for MVVM, then I wondered why Compose still remembers changes. When you drill down into the decompiled code of the StateFlow used in the view model, then you see its Flow.collectAsState() function encapsulates a remember call in producing state, and copies the StateFlow value to State value as a suspend function contained in a LaunchedEffect Composable. This qualifies as using coroutines.

The rest of the code-by-convention of keeping low-level knowledge of the model out of the UI is straight-forward. The App composable passes UI state and event actions, as lambda expressions, down to the Screen composable.

Sorting the list is easy. When items are passed to the LazyColumn, then use sortByDescending { it.timestamp } of the list of events.

The final count on the timer for learning, reading, experimenting and coding was 9 hours, 47 minutes, 9 seconds.
