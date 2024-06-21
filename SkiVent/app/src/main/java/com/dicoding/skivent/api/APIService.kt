package com.dicoding.skivent.api


import com.dicoding.skivent.dataclass.DetectionResponse
import com.dicoding.skivent.dataclass.HistoryItemItem
import com.dicoding.skivent.dataclass.HistoryResponse
import com.dicoding.skivent.dataclass.LoginDataAccount
import com.dicoding.skivent.dataclass.LoginResponse
import com.dicoding.skivent.dataclass.RegistResponse
import com.dicoding.skivent.dataclass.RegisterDataAccount
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @POST("register")
    fun registUser(
        @Body requestRegister: RegisterDataAccount
    ): Call<RegistResponse>

    @POST("login")
    fun loginUser(
        @Body requestLogin: LoginDataAccount
    ): Call<LoginResponse>

    @Multipart
    @POST("detect")
    suspend fun doDetect(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): DetectionResponse

    @GET("history")
    fun getHistory(
        @Header("Authorization") token: String
    ): HistoryResponse
}
