package com.example.newapp.breakingnew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newapp.data.NewsRepository
import com.example.newapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    private val refreshTriggerChannel = Channel<Unit>()
    private val refreshTrigger = refreshTriggerChannel.receiveAsFlow()

    var pendingScrollTopAfterRefresh = false

    val breakingNews = refreshTrigger.flatMapLatest {
        repository.getBreakingNews(
            onFetchSuccess = {
                pendingScrollTopAfterRefresh = true
            },
            onFetchFailed = { t ->
                viewModelScope.launch { eventChannel.send(Event.showErrorMessage(t))}
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun onStart() {
        if (breakingNews.value !is Resource.Loading){
            viewModelScope.launch {
                refreshTriggerChannel.send(Unit)
            }
        }
    }

    fun onManualRefresh() {
        if (breakingNews.value !is Resource.Loading){
            viewModelScope.launch {
                refreshTriggerChannel.send(Unit)
            }
        }
    }

    sealed class Event {
        data class showErrorMessage(val error: Throwable) : Event()
    }
}