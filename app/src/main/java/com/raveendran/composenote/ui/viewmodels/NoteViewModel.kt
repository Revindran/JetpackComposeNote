package com.raveendran.composenote.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendran.composenote.model.Note
import com.raveendran.composenote.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _list = MutableStateFlow(emptyList<Note>())
    val list: StateFlow<List<Note>> get() = _list

    private val _note = MutableSharedFlow<Note>()
    val note: SharedFlow<Note> = _note.shareWhileObserved(viewModelScope)

    init {
        getAll()
    }

    private fun getAllNotes(): Flow<List<Note>> = repository.getAllNotes()

    fun getNote(title: String) {
        viewModelScope.launch {
            repository.getNote(title = title).firstOrNull()?.let {
                _note.emit(it)
            }
        }
    }

    fun getAll() {
        getAllNotes()
            .onEach { _list.value = it }
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun searchANote(query: String) {
        _list.value = emptyList()
        searchNote(query = query)
            .onEach { _list.value = it }
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }


    fun searchNote(query: String): Flow<List<Note>> = repository.searchNote(query = query)


    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note = note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note = note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note = note)
        }
    }

    fun <T> Flow<T>.shareWhileObserved(coroutineScope: CoroutineScope) = shareIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(),
        replay = 1
    )

}