package com.dicoding.skivent.ui.dashboard.scan

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.skivent.api.APIService
import com.dicoding.skivent.dataclass.DetectionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class ScanResultViewModel(private val apiService: APIService) : ViewModel(){
    private val _predictionResult = MutableLiveData<DetectionResponse>()
    val predictionResult: LiveData<DetectionResponse> = _predictionResult

    fun predicSkin (imageUri: Uri, context: Context, token: String){
        viewModelScope.launch {
            try {
//                val realPath = getRealPathFromURI(imageUri, context)
//                Log.d("ScanResultViewModel", "Real path: $realPath")
//                val file = File(realPath)
//                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
//                Log.d("ScanResultViewModel", "Sending request to API")
//                val response = apiService.doDetect(token, body)
//                Log.d("ScanResultViewModel", "Received response: $response")
//                _predictionResult.value = response
                val compressedImageByteArray = compressImage(imageUri, context)
                Log.d("ScanResultViewModel", "Compressed image size: ${compressedImageByteArray.size} bytes")

                val requestFile = compressedImageByteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)

                Log.d("ScanResultViewModel", "Sending request to API")
                Log.d("ScanResultViewModel", "Token: $token")
                Log.d("ScanResultViewModel", "Request body type: ${body.body.contentType()}")

                val response = apiService.doDetect("Bearer $token", body)
                Log.d("ScanResultViewModel", "Received response: $response")
                _predictionResult.value = response
            } catch (e: Exception) {
                // Handle error
                Log.e("ScanResultViewModel", "Error during prediction", e)
                when (e) {
                    is retrofit2.HttpException -> {
                        Log.e("ScanResultViewModel", "HTTP Error: ${e.code()}")
                        Log.e("ScanResultViewModel", "Error Body: ${e.response()?.errorBody()?.string()}")
                    }
                    else -> Log.e("ScanResultViewModel", "Unexpected error: ${e.message}")
                }
                _predictionResult.value = DetectionResponse(null, false)
            }
        }
    }
    private suspend fun compressImage(imageUri: Uri, context: Context): ByteArray = withContext(
        Dispatchers.Default) {
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        val outputStream = ByteArrayOutputStream()
        var quality = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        while (outputStream.toByteArray().size > 1000000 && quality > 10) { // 1MB limit
            outputStream.reset()
            quality -= 10
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }
        outputStream.toByteArray()
    }

}