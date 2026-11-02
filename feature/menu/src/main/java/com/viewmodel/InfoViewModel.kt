package com.viewmodel

import androidx.lifecycle.ViewModel
import com.model.RestaurantInfo
import com.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewMode @Inject constructor(
    repository: RestaurantRepository
): ViewModel() {
    val info: RestaurantInfo = repository.info
}