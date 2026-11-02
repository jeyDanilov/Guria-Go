package com.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.model.OpenStatus
import com.model.RestaurantInfo
import com.repository.RestaurantRepository
import com.usecase.ObserveOpenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    repository: RestaurantRepository,
    observeOpenStatus: ObserveOpenStatus
) : ViewModel() {

    val info: RestaurantInfo = repository.info

    val openStatus: StateFlow<OpenStatus?> = observeOpenStatus()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}