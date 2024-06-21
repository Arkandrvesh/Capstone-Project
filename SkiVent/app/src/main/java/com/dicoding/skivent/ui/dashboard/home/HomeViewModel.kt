package com.dicoding.skivent.ui.dashboard.home

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.skivent.api.APIConfig
import com.dicoding.skivent.dataclass.HistoryItemItem
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel(), Parcelable {

    private val _historyList = MutableLiveData<List<HistoryItemItem>>()
    val historyList: LiveData<List<HistoryItemItem>> = _historyList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    constructor(parcel: Parcel) : this() {
    }

    fun getHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = APIConfig.getApiService().getHistory("Bearer YOUR_TOKEN_HERE")
                if (response.success) {
                    _historyList.value = response.history.flatten()
                } else {
                    _error.value = "Failed to fetch history"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeViewModel> {
        override fun createFromParcel(parcel: Parcel): HomeViewModel {
            return HomeViewModel(parcel)
        }

        override fun newArray(size: Int): Array<HomeViewModel?> {
            return arrayOfNulls(size)
        }
    }
}