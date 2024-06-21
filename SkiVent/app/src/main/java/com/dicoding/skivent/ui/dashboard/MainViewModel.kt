package com.dicoding.skivent.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.skivent.api.APIConfig
import com.dicoding.skivent.dataclass.LoginDataAccount
import com.dicoding.skivent.dataclass.LoginResponse
import com.dicoding.skivent.dataclass.RegistResponse
import com.dicoding.skivent.dataclass.RegisterDataAccount
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoadingLogin = MutableLiveData<Boolean>()
    val isLoadingLogin: LiveData<Boolean> = _isLoadingLogin
    var isErrorLogin: Boolean = false
    private val _messageLogin = MutableLiveData<String>()
    val messageLogin: LiveData<String> = _messageLogin
    private val _userLogin = MutableLiveData<LoginResponse>()
    val userLogin: LiveData<LoginResponse> = _userLogin
    var isErrorRegist: Boolean = false
    private val _isLoadingRegist = MutableLiveData<Boolean>()
    val isLoadingRegist: LiveData<Boolean> = _isLoadingRegist
    private val _messageRegist = MutableLiveData<String>()
    val messageRegist: LiveData<String> = _messageRegist
    fun getResponseLogin(loginDataAccount: LoginDataAccount) {
        _isLoadingLogin.value = true
        val api = APIConfig.getApiService().loginUser(loginDataAccount)
        api.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoadingLogin.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    isErrorLogin = false
                    _userLogin.value = responseBody!!
                    _messageLogin.value = "Halo ${_userLogin.value!!.loginResult.username}!"
                } else {
                    isErrorLogin = true
                    when (response.code()) {
                        401 -> _messageLogin.value =
                            "Email atau password yang anda masukan salah, silahkan coba lagi"
                        408 -> _messageLogin.value =
                            "Koneksi internet anda lambat, silahkan coba lagi"
                        else -> _messageLogin.value = "Pesan error: " + response.message()
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isErrorLogin = true
                _isLoadingLogin.value = false
                _messageLogin.value = "Pesan error: " + t.message.toString()
            }
        })
    }
    fun getResponseRegister(registDataUser: RegisterDataAccount) {
        _isLoadingRegist.value = true
        val api = APIConfig.getApiService().registUser(registDataUser)
        api.enqueue(object : retrofit2.Callback<RegistResponse> {
            override fun onResponse(
                call: Call<RegistResponse>,
                response: Response<RegistResponse>
            ) {
                _isLoadingRegist.value = false
                if (response.isSuccessful) {
                    isErrorRegist = false
                    _messageRegist.value = "Selamat akun Anda berhasil dibuat!!"
                } else {
                    isErrorRegist = true
                    when (response.code()) {
                        400 -> _messageRegist.value =
                            "1"
                        408 -> _messageRegist.value =
                            "Koneksi internet anda lambat, silahkan coba lagi"
                        else -> _messageRegist.value = "Pesan error: " + response.message()
                    }
                }
            }
            override fun onFailure(call: Call<RegistResponse>, t: Throwable) {
                isErrorRegist = true
                _isLoadingRegist.value = false
                _messageRegist.value = "Pesan error: " + t.message.toString()
            }
        })
    }
}