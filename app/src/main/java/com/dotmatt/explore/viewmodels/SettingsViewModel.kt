package com.dotmatt.explore.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _preferredLandmark = MutableStateFlow("")
    val preferredLandmark = _preferredLandmark.asStateFlow()

    init {
        _preferredLandmark.value = "Mountains"
    }
}