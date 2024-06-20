package com.dicoding.skivent.api


import com.dicoding.skivent.dataclass.LoginDataAccount
import com.dicoding.skivent.dataclass.RegisterDataAccount
import com.dicoding.skivent.dataclass.ResponseDetail
import com.dicoding.skivent.dataclass.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("register")
    fun registUser(@Body requestRegister: RegisterDataAccount): Call<ResponseDetail>
    @POST("login")
    fun loginUser(@Body requestLogin: LoginDataAccount): Call<ResponseLogin>
}
