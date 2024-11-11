package com.example.nvapp.ViewModel

import android.provider.MediaStore.Video
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseVieModel<ViewState, Event>: ViewModel(){
    private val _viewState = MutableStateFlow<ViewState?>(null)
    val viewState = _viewState.asStateFlow()
    fun updateState(viewState: ViewState) {
        _viewState.value = viewState
    }
    fun onTriggerEvent(event: Event) {

    }
}

data class ForyouState(
    val foryouPagefeed: List<Video>? = null,
    val isLoading: Boolean? = null,
    val error: String? = null
)

sealed class ForyouEvent{

}