package com.dicoding.skivent.ui.dashboard.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.skivent.api.APIService

class ViewModelFactory(private val apiService: APIService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScanResultViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}