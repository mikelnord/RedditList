package com.android.gb.redditlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.gb.redditlist.data.Repository
import com.android.gb.redditlist.model.RedditPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BaseViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){
    var accept: (UiAction) -> Unit
    val pagingDataFlow: Flow<PagingData<RedditPost>>

    init {
        val actionStateFlow = MutableSharedFlow<UiAction>(replay = 1)
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search) }

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
        pagingDataFlow = searches
            .flatMapLatest {getPosts() }
            .cachedIn(viewModelScope)
    }


    private fun getPosts(): Flow<PagingData<RedditPost>> =
        repository.getResultStream()
}

sealed class UiAction {
    object Search : UiAction()
}