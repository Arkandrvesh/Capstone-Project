package com.dicoding.skivent.api


import com.dicoding.skivent.dataclass.DetectionResponse
import com.dicoding.skivent.dataclass.LoginDataAccount
import com.dicoding.skivent.dataclass.RegisterDataAccount
import com.dicoding.skivent.dataclass.ResponseDetail
import com.dicoding.skivent.dataclass.ResponseLogin
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @POST("register")
    fun registUser(@Body requestRegister: RegisterDataAccount): Call<ResponseDetail>

    @POST("login")
    fun loginUser(@Body requestLogin: LoginDataAccount): Call<ResponseLogin>

    @Multipart
    @POST("detect")
    suspend fun doDetect(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): DetectionResponse
}
