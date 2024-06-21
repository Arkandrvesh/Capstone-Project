package com.dicoding.skivent.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.skivent.UserPreference
import kotlinx.coroutines.launch

class UserLoginViewModel(private val pref: UserPreference) : ViewModel() {

    fun getLoginSession(): LiveData<Boolean> {
        return pref.getLoginSession().asLiveData()
    }

    fun saveLoginSession(loginSession: Boolean) {
        viewModelScope.launch {
            pref.saveLoginSession(loginSession)
        }
    }

    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

    fun getUsername(): LiveData<String> {
        return pref.getUsername().asLiveData()
    }

    fun saveUsername(token: String) {
        viewModelScope.launch {
            pref.saveUsername(token)
        }
    }

    fun clearDataLogin() {
        viewModelScope.launch {
            pref.clearDataLogin()
        }
    }
}